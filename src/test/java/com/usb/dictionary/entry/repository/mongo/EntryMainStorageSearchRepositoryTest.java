package com.usb.dictionary.entry.repository.mongo;

import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.repository.elasticsearch.EntryFullTextSearchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(properties = {"spring.mongodb.embedded.version = 3.6.5"})
@ExtendWith(SpringExtension.class)
@DataMongoTest
class EntryMainStorageSearchRepositoryTest {

    private static final String SEARCH_WORD = "test";
    private static final String SOURCE_LANGUAGE = "en";

    @MockBean
    private EntryFullTextSearchRepository entryFullTextSearchRepository;

    @Autowired
    private EntryMainStorageRepository entryMainStorageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void findByWordAndSourceLanguageCode_success(){
        this.entryMainStorageRepository.save(Entry.builder().word(SEARCH_WORD).sourceLanguageCode(SOURCE_LANGUAGE).build());
        Optional<Entry> byWordAndSourceLanguageCode = this.entryMainStorageRepository
                .findByWordAndSourceLanguageCode(SEARCH_WORD, SOURCE_LANGUAGE);
        assertTrue(byWordAndSourceLanguageCode.isPresent());
    }

    @AfterEach
    void cleanUpDatabase() {
        mongoTemplate.getDb().drop();
    }
}