package com.usb.dictionary.searchentry.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.searchentry.model.Translation;
import com.usb.dictionary.searchentry.request.SearchEntryRequest;
import com.usb.dictionary.searchentry.response.SearchEntryResult;
import com.usb.dictionary.searchentry.model.SearchEntry;
import com.usb.dictionary.searchentry.model.SearchEntryDto;
import com.usb.dictionary.searchentry.repository.elasticsearch.SearchEntryFullTextSearchRepository;
import com.usb.dictionary.searchentry.service.SearchEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchEntryServiceImpl implements SearchEntryService {

    private final ObjectMapper objectMapper;

    private final SearchEntryFullTextSearchRepository searchEntryFullTextSearchRepository;

    private int pageSize = 10;

    @Override
    public SearchEntryResult search(SearchEntryRequest searchEntry){
        String word = searchEntry.getWord();
        String sourceLanguageCode = searchEntry.getSourceLanguageCode();
        PageRequest page = PageRequest.of(searchEntry.getPage(), pageSize);
        Page<SearchEntry> result = this.searchEntryFullTextSearchRepository.findByWordAndSourceLanguageCode(word
                , sourceLanguageCode
                , page);
        Page<SearchEntry> resultAlternatives = null;
        if(result.isEmpty()){
            resultAlternatives = this.searchEntryFullTextSearchRepository.findByWordAndSourceLanguageCodeWithFuzzy(word
                    , sourceLanguageCode
                    , page);
        }
        SearchEntryResult searchEntryResult = SearchEntryResult.builder()
                .entries(mapEntries(result)).entryAlternatives(mapEntries(resultAlternatives))
                .build();
        log.info("message=\"entry search result word:{}, sourceLanguageCode:{}, result:{}\"" +
                        ", feature=EntryServiceImpl, method=search"
                , word
                , sourceLanguageCode
                , searchEntryResult);
        return searchEntryResult;
    }

    private List<SearchEntryDto> mapEntries(Page<SearchEntry> entries){
        if(entries==null || isEmpty(entries.getContent())){
            return emptyList();
        }
        return mapEntries(entries.getContent());
    }

    private List<SearchEntryDto> mapEntries(Collection<SearchEntry> entries) {
        return entries.stream()
                .map(entry -> SearchEntryDto.builder().word(entry.getWord())
                        .sourceLanguageCode(entry.getSourceLanguageCode())
                        .type(entry.getType())
                        .translations(entry.getTranslations().stream()
                                .collect(toMap(Translation::getTargetLanguageCode, Translation::getMeaning))).build())
                .collect(toList());
    }

    @KafkaListener(topics = {EntryModified.TOPIC_NAME}, groupId = "dictionary")
    private void saveEntryToFullTextSearchRepository(String stringEntryModified){
        try {
            EntryModified entryModified = this.objectMapper.readValue(stringEntryModified, EntryModified.class);
            Optional<SearchEntry> existingWordOptional = this.searchEntryFullTextSearchRepository.findById(entryModified.getId());
            SearchEntry entry = null;
            if(existingWordOptional.isPresent()){
                entry= existingWordOptional.get();
                entry.getTranslations().clear();
                entry.setTranslations(toMapTranslations(entryModified.getTranslations()));
            }
            else
            {
                entry = SearchEntry.builder()
                        .sourceLanguageCode(entryModified.getSourceLanguageCode())
                        .type(entryModified.getType())
                        .word(entryModified.getWord()).translations(new ArrayList<>()).build();
                entry.setTranslations(toMapTranslations(entryModified.getTranslations()));
            }
            entry = this.searchEntryFullTextSearchRepository.save(entry);
            log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=saveEntryToElasticSearch", entry.getId());
        } catch (JsonProcessingException e) {
            log.error("message=\"an error occurred\", feature=EntryServiceImpl, method=entryModifiedEvent", e);
        }
    }

    private List<Translation> toMapTranslations(Map<String, String> map){
        List<Translation> translations = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            Translation translation = toMapTranslation(stringStringEntry);
            translations.add(translation);
        }
        return translations;
    }

    private Translation toMapTranslation(Map.Entry<String, String> stringStringEntry) {
        return Translation.builder()
                .meaning(stringStringEntry.getValue())
                .targetLanguageCode(stringStringEntry.getKey())
                .build();
    }
}
