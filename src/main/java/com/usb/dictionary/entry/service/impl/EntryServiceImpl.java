package com.usb.dictionary.entry.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.model.Translation;
import com.usb.dictionary.entry.repository.mongo.EntryMainStorageRepository;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.searchentry.repository.elasticsearch.SearchEntryFullTextSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.usb.dictionary.entry.file.EntryFileReader.readFromXlsxFile;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntryServiceImpl implements EntryService {

    private final EntryMainStorageRepository entryMainStorageRepository;

    private final SearchEntryFullTextSearchRepository entryFullTextSearchRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private void save(SaveEntryServiceRequest saveEntryServiceRequest) {
        Optional<Entry> existingWord
                = this.entryMainStorageRepository
                .findByWordAndSourceLanguageCode(saveEntryServiceRequest.getWord()
                        , saveEntryServiceRequest.getSourceLanguageCode());
        Entry entry = null;
        if(existingWord.isPresent()){
            entry = updateEntryTranslations(existingWord.get(), saveEntryServiceRequest.getTranslations());
            entry.setTags(saveEntryServiceRequest.getTags());
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
        if(saveEntryServiceRequest.getCreateCombinations() != null && saveEntryServiceRequest.getCreateCombinations()) {
            Map<String, String> translations = new HashMap<>(saveEntryServiceRequest.getTranslations());
            for (Map.Entry<String, String> sourceLanguageCodeMeaningPair : translations.entrySet()) {
                SaveEntryServiceRequest newEntrySaveRequest = SaveEntryServiceRequest.builder().type(saveEntryServiceRequest.getType())
                        .word(sourceLanguageCodeMeaningPair.getValue())
                        .sourceLanguageCode(sourceLanguageCodeMeaningPair.getKey())
                        .tags(saveEntryServiceRequest.getTags())
                        .translations(new HashMap<>()).build();
                newEntrySaveRequest.getTranslations().putAll(saveEntryServiceRequest.getTranslations());
                newEntrySaveRequest.getTranslations().put(saveEntryServiceRequest.getSourceLanguageCode(), saveEntryServiceRequest.getWord());
                newEntrySaveRequest.getTranslations().remove(sourceLanguageCodeMeaningPair.getKey());
                save(newEntrySaveRequest);
            }
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
                .id(entry.getId())
                .sourceLanguageCode(entry.getSourceLanguageCode())
                .word(entry.getWord())
                .type(entry.getType())
                .tags(entry.getTags())
                .version(entry.getVersion())
                .timestamp(LocalDateTime.now())
                .translations(entry.getTranslations().stream()
                        .collect(toMap(Translation::getTargetLanguageCode
                                , Translation::getMeaning))).build();
        try {
            this.kafkaTemplate.send(EntryModified.TOPIC_NAME, this.objectMapper.writeValueAsString(entryModified));
        } catch (JsonProcessingException e) {
            log.error("message=\"exception occurred\"", e);
        }
    }

    private Entry createNewEntry(SaveEntryServiceRequest saveEntryServiceRequest) {
        Entry newEntry = Entry.builder()
                .sourceLanguageCode(saveEntryServiceRequest.getSourceLanguageCode())
                .type(saveEntryServiceRequest.getType())
                .tags(saveEntryServiceRequest.getTags())
                .word(saveEntryServiceRequest.getWord()).translations(new ArrayList<>()).build();
        newEntry = updateEntryTranslations(newEntry, saveEntryServiceRequest.getTranslations());
        return newEntry;
    }

    private Entry updateEntryTranslations(Entry entry, Map<String, String> translations) {
        entry.getTranslations().clear();
        translations.forEach((targetLanguageCode, meaning)->
            entry.getTranslations().add(Translation.builder()
                    .targetLanguageCode(targetLanguageCode)
                    .meaning(meaning)
                    .build()));
        return entry;
    }
}
