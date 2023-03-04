package com.usb.dictionary.entry.repository.mongodb;

import com.usb.dictionary.entry.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryMainStorageRepository extends MongoRepository<Entry, String> {
}
