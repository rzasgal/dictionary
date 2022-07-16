package com.usb.dictionary.entry.repository.elasticsearch;

import com.usb.dictionary.entry.model.Entry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryElasticsearchRepository extends ElasticsearchRepository<Entry, String> {

}
