package com.usb.dictionary.word.repository.neo4j;

import com.usb.dictionary.word.service.model.Word;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends Neo4jRepository<Word, Long> {
  Optional<Word> findByContent(String content);
}
