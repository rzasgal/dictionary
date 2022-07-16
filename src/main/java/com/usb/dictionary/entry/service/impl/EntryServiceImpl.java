package com.usb.dictionary.entry.service.impl;

import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.model.Translation;
import com.usb.dictionary.entry.repository.mongo.EntryMongoRepository;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final EntryMongoRepository entryMongoRepository;


    @Override
    public void save(SaveEntryServiceRequest saveEntryServiceRequest) {
        Entry newEntry = Entry.builder()
                .sourceLanguageCode(saveEntryServiceRequest.getSourceLanguageCode())
                .type(saveEntryServiceRequest.getType())
                .word(saveEntryServiceRequest.getWord()).translations(new ArrayList<>()).build();
        saveEntryServiceRequest.getTranslations().forEach((targetLanguageCode, meaning)->newEntry.getTranslations()
                .add(Translation.builder().targetLanguageCode(targetLanguageCode).meaning(meaning).build()));
        this.entryMongoRepository.save(newEntry);
    }

}
