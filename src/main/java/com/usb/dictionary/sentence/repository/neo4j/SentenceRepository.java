package com.usb.dictionary.sentence.repository.neo4j;

import com.usb.dictionary.sentence.model.Sentence;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SentenceRepository extends Neo4jRepository<Sentence, Long> {}
