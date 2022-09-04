package com.usb.dictionary.searchentry.repository.elasticsearch;

import com.usb.dictionary.searchentry.model.SearchEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchEntryFullTextSearchRepository extends ElasticsearchRepository<SearchEntry, String> {
    Optional<SearchEntry> findByWord(String word);

    Page<SearchEntry> findByWordAndSourceLanguageCode(String word, String sourceLanguageCode, PageRequest pageRequest);

    Page<SearchEntry> findByTagsAndSourceLanguageCode(String tag, String sourceLanguageCode, PageRequest pageRequest);

    @Query(value = "{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [{\n" +
            "          \"match\": {\n" +
            "            \"sourceLanguageCode\": \"?1\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"match\": {\n" +
            "          \"word\": {\n" +
            "            \"query\": \"?0\"\n" +
            "            , \"fuzziness\": 2\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "      ]\n" +
            "      \n" +
            "    } \n" +
            "\n" +
            "  }")
    Page<SearchEntry> findByWordAndSourceLanguageCodeWithFuzzy(String word, String sourceLanguageCode, PageRequest pageRequest);
}
