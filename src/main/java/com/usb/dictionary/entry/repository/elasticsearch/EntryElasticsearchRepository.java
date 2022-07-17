package com.usb.dictionary.entry.repository.elasticsearch;

import com.usb.dictionary.entry.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryElasticsearchRepository extends ElasticsearchRepository<Entry, String> {
    Optional<Entry> findByWord(String word);

    Page<Entry> findByWordAndSourceLanguageCode(String word, String sourceLanguageCode, PageRequest pageRequest);
}
