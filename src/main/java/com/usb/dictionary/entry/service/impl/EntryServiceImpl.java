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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public void save(SaveEntryServiceRequest saveEntryServiceRequest) {
        var entry = createNewEntry(saveEntryServiceRequest);
        if(StringUtils.hasText(entry.getId())){
            this.entryMainStorageRepository.findById(entry.getId()).ifPresent(existingEntry ->{
                entry.setVersion(existingEntry.getVersion());
            });
        }
        fireSaveEvent(entry);
        log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=save", entry.getId());
    }

    @Override
    public void delete(String id){
        this.entryMainStorageRepository.deleteById(id);
        this.fireDeleteEvent(id);
        log.info("message=\"entry deleted id:{}\", feature=EntryServiceImpl, method=save", id);
    }

    @Override
    public void readFromFile( ) throws IOException {
        readFromXlsxFile(ReadFromXlsxFileServiceRequest.builder().fileName("words.xlsx")
                .sheetIndex(0)
                .wordIndex(0)
                .meaningIndex(1)
                .typeIndex(2).build())
                .forEach(this::save);
    }

    @Override
    public void readFromFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException {
        readFromXlsxFile(readFromXlsxFileServiceRequest).forEach(this::save);
    }

    private void fireSaveEvent(Entry entry) {
        EntryModified entrySaved = EntryModified.builder()
                .id(entry.getId())
                .type(entry.getType())
                .tags(entry.getTags())
                .words(entry.getWords().stream().map(this.entryServiceMapper::toWordDto).toList())
                .build();
        try {
            this.kafkaTemplate.send(EntryModified.TOPIC_NAME, this.objectMapper.writeValueAsString(entrySaved));
        } catch (JsonProcessingException e) {
            log.error("message=\"exception occurred\"", e);
        }
    }

    private void fireDeleteEvent(String id) {
        EntryModified entryDeleted = EntryModified.builder()
                .id(id).deleted(true).build();
        try {
            this.kafkaTemplate.send(EntryModified.TOPIC_NAME, this.objectMapper.writeValueAsString(entryDeleted));
        } catch (JsonProcessingException e) {
            log.error("message=\"exception occurred\"", e);
        }
    }

    private Entry createNewEntry(SaveEntryServiceRequest saveEntryServiceRequest) {
        EntryServiceRequestDto entry = saveEntryServiceRequest.getEntry();
        return Entry.builder()
                .id(saveEntryServiceRequest.getEntry().getId())
                .tags(entry.getTags())
                .type(entry.getType())
                .words(entry.getWords().stream().map(this.entryServiceMapper::toWord).toList())
                .build();
    }
}
