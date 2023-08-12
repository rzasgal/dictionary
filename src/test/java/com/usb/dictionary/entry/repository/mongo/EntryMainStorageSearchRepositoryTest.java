package com.usb.dictionary.entry.repository.mongo;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.model.Word;
import com.usb.dictionary.entry.repository.mongodb.EntryMainStorageRepository;
import com.usb.dictionary.searchentry.repository.elasticsearch.SearchEntryFullTextSearchRepository;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource(properties = {"spring.mongodb.embedded.version = 3.6.5"})
@ExtendWith(SpringExtension.class)
@DataMongoTest
class EntryMainStorageSearchRepositoryTest {

  private static final String SEARCH_WORD = "test";
  private static final String SOURCE_LANGUAGE = "en";

  @MockBean private SearchEntryFullTextSearchRepository entryFullTextSearchRepository;

  @Autowired private EntryMainStorageRepository entryMainStorageRepository;

  @Autowired private MongoTemplate mongoTemplate;

  @Test
  public void findByWordAndSourceLanguageCode_success() {
    Entry savedEntry =
        this.entryMainStorageRepository.save(
            Entry.builder()
                .words(
                    new HashSet<>(
                        asList(
                            Word.builder()
                                .name(SEARCH_WORD)
                                .languageCode(SOURCE_LANGUAGE)
                                .build())))
                .build());
    Optional<Entry> byWordAndSourceLanguageCode =
        this.entryMainStorageRepository.findById(savedEntry.getId());
    assertTrue(byWordAndSourceLanguageCode.isPresent());
  }

  @AfterEach
  void cleanUpDatabase() {
    mongoTemplate.getDb().drop();
  }
}
