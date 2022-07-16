package com.usb.dictionary.entry.repository.mongo;

import com.usb.dictionary.entry.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryMongoRepository extends MongoRepository<Entry, String> {
}
