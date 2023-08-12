package com.usb.dictionary.word.repository.elasticsearch;

import com.usb.dictionary.word.service.model.WordSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordSearchRepository extends ElasticsearchRepository<WordSearch, Long> {
  Page<WordSearch> findByContent(String content, PageRequest pageRequest);

  @Query(
      value =
          "{\n"
              + "  \"function_score\": {\n"
              + "    \"query\":{\n"
              + "      \"term\":{\n"
              + "        \"languageCode\": {\n"
              + "          \"value\": \"?0\"\n"
              + "        }\n"
              + "      }\n"
              + "      },\n"
              + "      \"random_score\": {}\n"
              + "    }\n"
              + "}")
  Page<WordSearch> findRandomWords(String languageCode, PageRequest pageRequest);

  Page<WordSearch> searchByTags(String tag, PageRequest page);
}
