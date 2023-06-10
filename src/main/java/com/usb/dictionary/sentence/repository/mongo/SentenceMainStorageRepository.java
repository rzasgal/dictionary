package com.usb.dictionary.sentence.repository.mongo;

import com.usb.dictionary.sentence.model.Sentence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SentenceMainStorageRepository extends MongoRepository<Sentence, String> {
    Optional<Sentence> findByContent(String sentence);
    Page<Sentence> findByEntryIds(Set<String> entryIds, PageRequest pageRequest);
}
