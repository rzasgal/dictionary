package com.usb.dictionary.word.repository.neo4j;

import com.usb.dictionary.word.service.model.Meaning;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeaningRepository extends Neo4jRepository<Meaning, Long> {}
