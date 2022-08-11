package com.usb.dictionary.searchentry.repository.elasticsearch;

import com.usb.dictionary.configuration.TestElasticSearchContainer;
import com.usb.dictionary.searchentry.model.SearchEntry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.CollectionUtils.isEmpty;

@ExtendWith(SpringExtension.class)
@EnableElasticsearchRepositories
@TestPropertySource(properties = {"spring.elasticsearch.uris = http://127.0.0.1:9500"})
@ContextConfiguration(classes = {ElasticsearchRestClientAutoConfiguration.class
        , ElasticsearchDataAutoConfiguration.class})
@Testcontainers
class SearchEntryFullTextSearchRepositoryTest {

    @Autowired
    public SearchEntryFullTextSearchRepository searchEntryFullTextSearchRepository;

    @Container
    private static ElasticsearchContainer container = new TestElasticSearchContainer();

    @BeforeAll
    public static void beforeAll(){
        container.start();
    }

    @AfterAll
    public static void afterAll(){
        container.stop();
    }

    @Test
    public void findByWord_success(){
        this.searchEntryFullTextSearchRepository.save(SearchEntry.builder().word("test").build());
        Optional<SearchEntry> word = this.searchEntryFullTextSearchRepository.findByWord("test");
        assertTrue(word.isPresent());
    }

    @Test
    public void findByWordAndSourceLanguageCode_success(){
        this.searchEntryFullTextSearchRepository.save(SearchEntry.builder().word("test").sourceLanguageCode("en").build());
        Page<SearchEntry> word = this.searchEntryFullTextSearchRepository.findByWordAndSourceLanguageCode("test"
                , "en", PageRequest.of(0, 10));
        assertFalse(isEmpty(word.getContent()));
    }

    @Test
    public void findByWordAndSourceLanguageCodeWithFuzzy_success(){
        this.searchEntryFullTextSearchRepository.save(SearchEntry.builder().word("tes").sourceLanguageCode("en").build());
        Page<SearchEntry> word = this.searchEntryFullTextSearchRepository.findByWordAndSourceLanguageCodeWithFuzzy("test"
                , "en", PageRequest.of(0, 10));
        assertFalse(isEmpty(word.getContent()));
    }
}