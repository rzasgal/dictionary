package com.usb.dictionary.tag.repository.neo4j;

import com.usb.dictionary.tag.model.Tag;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends Neo4jRepository<Tag, Long> {
  Optional<Tag> findByName(String name);
}
