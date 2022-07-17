package com.usb.dictionary.entry.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.model.EntryDto;
import com.usb.dictionary.entry.model.Translation;
import com.usb.dictionary.entry.repository.elasticsearch.EntryElasticsearchRepository;
import com.usb.dictionary.entry.repository.mongo.EntryMongoRepository;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.entry.service.request.SearchEntry;
import com.usb.dictionary.entry.service.response.SearchEntryResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntryServiceImpl implements EntryService {

    private final EntryMongoRepository entryMongoRepository;

    private final EntryElasticsearchRepository entryElasticsearchRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private int pageSize = 10;

    @Override
    public SearchEntryResult search(SearchEntry searchEntry){
        Page<Entry> result = this.entryElasticsearchRepository.findByWordAndSourceLanguageCode(searchEntry.getWord()
                , searchEntry.getSourceLanguageCode()
                , PageRequest.of(searchEntry.getPage(), pageSize));
        SearchEntryResult searchEntryResult = SearchEntryResult.builder().entries(result.getContent().stream()
                .map(entry -> EntryDto.builder().word(entry.getWord())
                        .sourceLanguageCode(entry.getSourceLanguageCode())
                        .type(entry.getType())
                        .translations(entry.getTranslations().stream()
                                .collect(toMap(Translation::getTargetLanguageCode, Translation::getMeaning))).build())
                .collect(toList())).build();
        log.info("message=\"entry search result word:{}, sourceLanguageCode:{}, result:{}\"" +
                ", feature=EntryServiceImpl, method=search"
                , searchEntry.getWord()
                , searchEntry.getSourceLanguageCode()
                , searchEntryResult);
        return searchEntryResult;
    }

    @Override
    public void save(SaveEntryServiceRequest saveEntryServiceRequest) {
        Optional<Entry> existingWord
                = this.entryMongoRepository
                .findByWordAndSourceLanguageCode(saveEntryServiceRequest.getWord()
                        , saveEntryServiceRequest.getSourceLanguageCode());
        Entry entry = null;
        if(existingWord.isPresent()){
            entry = updateExistingEntry(existingWord.get(), saveEntryServiceRequest.getTranslations());
        }
        else {
           entry = createNewEntry(saveEntryServiceRequest);
        }
        entry = this.entryMongoRepository.save(entry);
        log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=save", entry.getId());
        generateEntryModifiedEventForSave(entry);
    }

    private void generateEntryModifiedEventForSave(Entry entry) {
        EntryModified entryModified = EntryModified.builder()
                .sourceLanguageCode(entry.getSourceLanguageCode())
                .word(entry.getWord())
                .type(entry.getType())
                .translations(entry.getTranslations().stream()
                        .collect(toMap(Translation::getTargetLanguageCode
                                , Translation::getMeaning))).build();
        try {
            this.kafkaTemplate.send(EntryModified.TOPIC_NAME, this.objectMapper.writeValueAsString(entryModified));
        } catch (JsonProcessingException e) {

        }
    }

    private Entry updateExistingEntry(Entry entry, Map<String, String> translations) {
        entry.getTranslations().clear();
        entry = updateEntry(entry, translations);
        return entry;
    }

    private Entry createNewEntry(SaveEntryServiceRequest saveEntryServiceRequest) {
        Entry newEntry = Entry.builder()
                .sourceLanguageCode(saveEntryServiceRequest.getSourceLanguageCode())
                .type(saveEntryServiceRequest.getType())
                .word(saveEntryServiceRequest.getWord()).translations(new ArrayList<>()).build();
        newEntry = updateEntry(newEntry, saveEntryServiceRequest.getTranslations());
        return newEntry;
    }

    private Entry updateEntry(Entry entry, Map<String, String> translations) {
        translations.forEach((targetLanguageCode, meaning)->entry.getTranslations()
                .add(Translation.builder().targetLanguageCode(targetLanguageCode).meaning(meaning).build()));
        return entry;
    }

    @KafkaListener(topics = {EntryModified.TOPIC_NAME}, groupId = "dictionary")
    private void saveEntryToElasticSearch(String stringEntryModified){
        try {
            EntryModified entryModified = this.objectMapper.readValue(stringEntryModified, EntryModified.class);
            Optional<Entry> existingWordOptional = this.entryElasticsearchRepository.findByWord(entryModified.getWord());
            Entry entry = null;
            if(existingWordOptional.isPresent()){
                entry= existingWordOptional.get();
                entry = updateExistingEntry(entry, entryModified.getTranslations());
            }
            else
            {
                entry = Entry.builder()
                        .sourceLanguageCode(entryModified.getSourceLanguageCode())
                        .type(entryModified.getType())
                        .word(entryModified.getWord()).translations(new ArrayList<>()).build();
                entry = updateEntry(entry, entryModified.getTranslations());
            }
            entry = this.entryElasticsearchRepository.save(entry);
            log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=saveEntryToElasticSearch", entry.getId());
        } catch (JsonProcessingException e) {
            log.error("message=\"an error occurred\", feature=EntryServiceImpl, method=entryModifiedEvent", e);
        }
    }

}
