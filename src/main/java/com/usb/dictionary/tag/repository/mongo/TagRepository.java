package com.usb.dictionary.tag.repository.mongo;

import com.usb.dictionary.tag.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
    Optional<Tag> findByName(String name);
}
