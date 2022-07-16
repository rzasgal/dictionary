package com.usb.dictionary.entry.service.impl;

import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.model.Translation;
import com.usb.dictionary.entry.repository.mongo.EntryMongoRepository;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final EntryMongoRepository entryMongoRepository;


    @Override
    public void save(SaveEntryServiceRequest saveEntryServiceRequest) {
        Optional<Entry> existingWord
                = this.entryMongoRepository
                .findByWordAndSourceLanguageCode(saveEntryServiceRequest.getWord()
                        , saveEntryServiceRequest.getSourceLanguageCode());
        if(existingWord.isPresent()){
            updateExistingEntry(existingWord.get(), saveEntryServiceRequest.getTranslations());
        }
        else {
            createNewEntry(saveEntryServiceRequest);
        }
    }

    private void updateExistingEntry(Entry entry, Map<String, String> translations) {
        entry.getTranslations().clear();
        updateEntry(entry, translations);
        this.entryMongoRepository.save(entry);
    }

    private void createNewEntry(SaveEntryServiceRequest saveEntryServiceRequest) {
        Entry newEntry = Entry.builder()
                .sourceLanguageCode(saveEntryServiceRequest.getSourceLanguageCode())
                .type(saveEntryServiceRequest.getType())
                .word(saveEntryServiceRequest.getWord()).translations(new ArrayList<>()).build();
        updateEntry(newEntry, saveEntryServiceRequest.getTranslations());
        this.entryMongoRepository.save(newEntry);
    }

    private void updateEntry(Entry entry, Map<String, String> translations) {
        translations.forEach((targetLanguageCode, meaning)->entry.getTranslations()
                .add(Translation.builder().targetLanguageCode(targetLanguageCode).meaning(meaning).build()));
    }

}
