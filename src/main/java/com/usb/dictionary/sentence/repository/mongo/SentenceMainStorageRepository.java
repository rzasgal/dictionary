package com.usb.dictionary.sentence.repository.mongo;

import com.usb.dictionary.sentence.model.Sentence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SentenceMainStorageRepository extends MongoRepository<Sentence, String> {
    Optional<Sentence> findByContent(String sentence);
}
