package com.usb.dictionary.entry.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.model.EntryDto;
import com.usb.dictionary.entry.model.Translation;
import com.usb.dictionary.entry.repository.elasticsearch.EntryFullTextSearchRepository;
import com.usb.dictionary.entry.repository.mongo.EntryMainStorageRepository;
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

import java.io.IOException;
import java.util.*;

import static com.usb.dictionary.entry.file.EntryFileReader.readFromXlsxFile;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntryServiceImpl implements EntryService {

    private final EntryMainStorageRepository entryMainStorageRepository;

    private final EntryFullTextSearchRepository entryFullTextSearchRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private int pageSize = 10;

    @Override
    public SearchEntryResult search(SearchEntry searchEntry){
        String word = searchEntry.getWord();
        String sourceLanguageCode = searchEntry.getSourceLanguageCode();
        PageRequest page = PageRequest.of(searchEntry.getPage(), pageSize);
        Page<Entry> result = this.entryFullTextSearchRepository.findByWordAndSourceLanguageCode(word
                , sourceLanguageCode
                , page);
        Page<Entry> resultAlternatives = null;
        if(result.isEmpty()){
            resultAlternatives = this.entryFullTextSearchRepository.findByWordAndSourceLanguageCodeWithFuzzy(word
                    , sourceLanguageCode
                    , page);
        }
        SearchEntryResult searchEntryResult = SearchEntryResult.builder()
                .entries(mapEntries(result)).entriesAlternatives(mapEntries(resultAlternatives))
                .build();
        log.info("message=\"entry search result word:{}, sourceLanguageCode:{}, result:{}\"" +
                ", feature=EntryServiceImpl, method=search"
                , word
                , sourceLanguageCode
                , searchEntryResult);
        return searchEntryResult;
    }

    private List<EntryDto> mapEntries(Page<Entry> entries){
        if(entries==null || isEmpty(entries.getContent())){
            return emptyList();
        }
        return mapEntries(entries.getContent());
    }

    private List<EntryDto> mapEntries(Collection<Entry> entries) {
        return entries.stream()
                .map(entry -> EntryDto.builder().word(entry.getWord())
                        .sourceLanguageCode(entry.getSourceLanguageCode())
                        .type(entry.getType())
                        .translations(entry.getTranslations().stream()
                                .collect(toMap(Translation::getTargetLanguageCode, Translation::getMeaning))).build())
                .collect(toList());
    }

    @Override
    public void save(SaveEntryServiceRequest saveEntryServiceRequest) {
        Optional<Entry> existingWord
                = this.entryMainStorageRepository
                .findByWordAndSourceLanguageCode(saveEntryServiceRequest.getWord()
                        , saveEntryServiceRequest.getSourceLanguageCode());
        Entry entry = null;
        if(existingWord.isPresent()){
            entry = updateExistingEntry(existingWord.get(), saveEntryServiceRequest.getTranslations());
        }
        else {
           entry = createNewEntry(saveEntryServiceRequest);
        }
        entry = this.entryMainStorageRepository.save(entry);
        log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=save", entry.getId());
        generateEntryModifiedEventForSave(entry);
    }

    @Override
    public void saveCombination(SaveEntryServiceRequest saveEntryServiceRequest){
        save(saveEntryServiceRequest);
        Map<String, String> translations = new HashMap<>(saveEntryServiceRequest.getTranslations());
        for (Map.Entry<String, String> sourceLanguageCodeMeaningPair : translations.entrySet()) {
            SaveEntryServiceRequest newEntrySaveRequest = SaveEntryServiceRequest.builder().type(saveEntryServiceRequest.getType())
                    .word(sourceLanguageCodeMeaningPair.getValue())
                    .sourceLanguageCode(sourceLanguageCodeMeaningPair.getKey())
                    .translations(new HashMap<>()).build();
            newEntrySaveRequest.getTranslations().putAll(saveEntryServiceRequest.getTranslations());
            newEntrySaveRequest.getTranslations().put(saveEntryServiceRequest.getSourceLanguageCode(), saveEntryServiceRequest.getWord());
            newEntrySaveRequest.getTranslations().remove(sourceLanguageCodeMeaningPair.getKey());
            save(newEntrySaveRequest);
        }
    }

    @Override
    public void readFromFile( ) throws IOException {
        readFromXlsxFile(ReadFromXlsxFileServiceRequest.builder().fileName("words.xlsx")
                .sheetIndex(0)
                .wordIndex(0)
                .meaningIndex(1)
                .typeIndex(2).build()).forEach(this::saveCombination);

    }

    @Override
    public void readFromFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException {
        readFromXlsxFile(readFromXlsxFileServiceRequest).forEach(this::saveCombination);
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
    private void saveEntryToFullTextSearchRepository(String stringEntryModified){
        try {
            EntryModified entryModified = this.objectMapper.readValue(stringEntryModified, EntryModified.class);
            Optional<Entry> existingWordOptional = this.entryFullTextSearchRepository.findByWord(entryModified.getWord());
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
            entry = this.entryFullTextSearchRepository.save(entry);
            log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=saveEntryToElasticSearch", entry.getId());
        } catch (JsonProcessingException e) {
            log.error("message=\"an error occurred\", feature=EntryServiceImpl, method=entryModifiedEvent", e);
        }
    }

}
