package com.usb.dictionary.entry.repository.mongo;

import com.usb.dictionary.entry.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryMainStorageRepository extends MongoRepository<Entry, String> {
    Optional<Entry> findByWordAndSourceLanguageCode(String word, String sourceLanguageCode);
}
