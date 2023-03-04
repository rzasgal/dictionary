package com.usb.dictionary.searchentry.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.searchentry.model.SearchEntry;
import com.usb.dictionary.searchentry.repository.elasticsearch.SearchEntryFullTextSearchRepository;
import com.usb.dictionary.searchentry.request.SearchEntryRequest;
import com.usb.dictionary.searchentry.response.SearchEntryResult;
import com.usb.dictionary.searchentry.service.SearchEntryService;
import com.usb.dictionary.searchentry.service.mapper.SearchEntryServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchEntryServiceImpl implements SearchEntryService {

    private final SearchEntryServiceMapper searchEntryServiceMapper;

    private final ObjectMapper objectMapper;

    private final SearchEntryFullTextSearchRepository searchEntryFullTextSearchRepository;

    private int pageSize = 10;

    @Override
    public SearchEntryResult search(SearchEntryRequest searchEntry){
        String word = searchEntry.getWord();
        String tag = searchEntry.getTag();
        String languageCode = searchEntry.getSourceLanguageCode();
        PageRequest page = PageRequest.of(searchEntry.getPage(), pageSize);
        if(StringUtils.hasText(word)) {
            return searchByWord(word, languageCode, page);
        }
        else if(StringUtils.hasText(tag)){
            return searchByTag(tag, page);
        }
        else{
            return SearchEntryResult.builder().build();
        }
    }

    private SearchEntryResult searchByTag(String tag, PageRequest page) {
        Page<SearchEntry> result = this.searchEntryFullTextSearchRepository.findByTags(tag, page);
        SearchEntryResult searchEntryResult = SearchEntryResult.builder()
                .entries(result.get().map(this.searchEntryServiceMapper::toSearchEntryDto).toList())
                .build();
        log.info("message=\"entry search result tag:{}, result:{}\"" +
                        ", feature=EntryServiceImpl, method=searchByTag"
                , tag
                , searchEntryResult);
        return searchEntryResult;
    }

    private SearchEntryResult searchByWord(String word, String languageCode, PageRequest page) {
        Page<SearchEntry> result = this.searchEntryFullTextSearchRepository.findByWordsAndLanguageCode(word
                , languageCode
                , page);
        SearchEntryResult searchEntryResult = null;
        if(result.getTotalElements() == 0) {
            result = this.searchEntryFullTextSearchRepository.findByWordsAndLanguageCodeWithFuzzy(word
                    , languageCode
                    , page);
        }
        searchEntryResult = SearchEntryResult.builder()
                .entries(result.get().map(this.searchEntryServiceMapper::toSearchEntryDto).toList())
                .build();
        log.info("message=\"entry search result word:{}, languageCode:{}, result:{}\"" +
                        ", feature=EntryServiceImpl, method=searchByWord"
                , word
                , languageCode
                , searchEntryResult);
        return searchEntryResult;
    }

    @KafkaListener(topics = {EntryModified.TOPIC_NAME}, groupId = "dictionary.searchentry")
    private void saveEntryToFullTextSearchRepository(String stringEntryModified){
        try {
            EntryModified entryModified = this.objectMapper.readValue(stringEntryModified, EntryModified.class);
            SearchEntry entry = this.searchEntryServiceMapper.toSearchEntry(entryModified);
            entry = this.searchEntryFullTextSearchRepository.save(entry);
            log.info("message=\"entry saved id:{}\", feature=EntryServiceImpl, method=saveEntryToElasticSearch", entry.getId());
        } catch (JsonProcessingException e) {
            log.error("message=\"an error occurred\", feature=EntryServiceImpl, method=entryModifiedEvent", e);
        }
    }
}
