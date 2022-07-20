package com.usb.dictionary.entry.repository.elasticsearch;

import com.usb.dictionary.entry.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryFullTextSearchRepository extends ElasticsearchRepository<Entry, String> {
    Optional<Entry> findByWord(String word);

    Page<Entry> findByWordAndSourceLanguageCode(String word, String sourceLanguageCode, PageRequest pageRequest);

    @Query(value = "{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [{\n" +
            "          \"match\": {\n" +
            "            \"sourceLanguageCode\": \"?1\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"fuzzy\": {\n" +
            "          \"word\": {\n" +
            "            \"value\": \"?0\"\n" +
            "            , \"fuzziness\": 2\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "      ]\n" +
            "      \n" +
            "    } \n" +
            "\n" +
            "  }")
    Page<Entry> findByWordAndSourceLanguageCodeWithFuzzy(String word, String sourceLanguageCode, PageRequest pageRequest);
}
