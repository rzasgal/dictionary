package com.usb.dictionary.entry.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.repository.mongodb.EntryMainStorageRepository;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.mapper.EntryServiceMapper;
import com.usb.dictionary.entry.service.request.EntryServiceRequestDto;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.searchentry.repository.elasticsearch.SearchEntryFullTextSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.usb.dictionary.entry.file.EntryFileReader.readFromXlsxFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntryServiceImpl implements EntryService {

    private final EntryServiceMapper entryServiceMapper;
    private final EntryMainStorageRepository entryMainStorageRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void saveEntry(SaveEntryServiceRequest saveEntryServiceRequest) {
        Entry entry = createNewEntry(saveEntryServiceRequest);
        entry = this.entryMainStorageRepository.save(entry);
        generateEntryModifiedEventForSave(entry);
        log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=save", entry.getId());
    }

    @Override
    public void readFromFile( ) throws IOException {
        readFromXlsxFile(ReadFromXlsxFileServiceRequest.builder().fileName("words.xlsx")
                .sheetIndex(0)
                .wordIndex(0)
                .meaningIndex(1)
                .typeIndex(2).build())
                .forEach(this::saveEntry);
    }

    @Override
    public void readFromFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException {
        readFromXlsxFile(readFromXlsxFileServiceRequest).forEach(this::saveEntry);
    }

    private void generateEntryModifiedEventForSave(Entry entry) {
        EntryModified entryModified = EntryModified.builder()
                .id(entry.getId())
                .tags(entry.getTags())
                .words(entry.getWords().stream().map(this.entryServiceMapper::toWordDto).toList())
                .build();
        try {
            this.kafkaTemplate.send(EntryModified.TOPIC_NAME, this.objectMapper.writeValueAsString(entryModified));
        } catch (JsonProcessingException e) {
            log.error("message=\"exception occurred\"", e);
        }
    }

    private Entry createNewEntry(SaveEntryServiceRequest saveEntryServiceRequest) {
        EntryServiceRequestDto entry = saveEntryServiceRequest.getEntry();
        return Entry.builder()
                .tags(entry.getTags())
                .words(entry.getWords().stream().map(this.entryServiceMapper::toWord).toList())
                .build();
    }
}
