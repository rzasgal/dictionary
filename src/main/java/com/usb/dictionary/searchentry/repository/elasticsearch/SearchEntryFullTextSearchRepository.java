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

    @Query(value = "{\n" +
            "    \"nested\": {\n" +
            "      \"path\": \"words\",\n" +
            "      \"query\": {\n" +
            "        \"bool\": {\n" +
            "          \"must\": [\n" +
            "            {\n" +
            "              \"match\": {\n" +
            "                \"words.name\": \"?0\"\n" +
            "              }\n" +
            "            },\n" +
            "            {\n" +
            "              \"match\": {\n" +
            "                \"words.languageCode\": \"?1\"\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "}")
    Page<SearchEntry> findByWordsAndLanguageCode(String word, String languageCode, PageRequest pageRequest);

    @Query(value = "{\n" +
            "  \"nested\": {\n" +
            "    \"path\": \"words\",\n" +
            "    \"query\": {\n" +
            "      \"bool\": {\n" +
            "        \"must\": [\n" +
            "          {\n" +
            "            \"match\": {\n" +
            "              \"words.name\": {\n" +
            "                \"query\": \"?0\",\n" +
            "                \"fuzziness\": 2\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"match\": {\n" +
            "              \"words.languageCode\": \"?1\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}")
    Page<SearchEntry> findByWordsAndLanguageCodeWithFuzzy(String word, String languageCode, PageRequest pageRequest);

    Page<SearchEntry> findByTags(String tag, PageRequest pageRequest);

    @Query(value = "{\n" +
            "    \"function_score\": {\n" +
            "      \"query\": {\n" +
            "        \"nested\": {\n" +
            "          \"path\": \"words\",\n" +
            "          \"query\": {\n" +
            "            \"term\": {\n" +
            "              \"words.languageCode\": {\n" +
            "                \"value\": \"?0\"\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"random_score\": {}\n" +
            "    }\n" +
            "  }")
    Page<SearchEntry> findRandom(String languageCode, PageRequest pageRequest);
}
