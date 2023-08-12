package com.usb.dictionary.word.service.impl;

import static java.util.function.Function.identity;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.word.event.WordModifiedEvent;
import com.usb.dictionary.word.repository.elasticsearch.WordSearchRepository;
import com.usb.dictionary.word.repository.neo4j.MeaningRepository;
import com.usb.dictionary.word.repository.neo4j.WordRepository;
import com.usb.dictionary.word.service.WordService;
import com.usb.dictionary.word.service.event.MeaningModifiedEvent;
import com.usb.dictionary.word.service.model.Meaning;
import com.usb.dictionary.word.service.model.MeaningDto;
import com.usb.dictionary.word.service.model.Word;
import com.usb.dictionary.word.service.model.WordDto;
import com.usb.dictionary.word.service.model.WordSearch;
import com.usb.dictionary.word.service.model.WordWithMeaningsDto;
import com.usb.dictionary.word.service.request.FindWordsServiceRequest;
import com.usb.dictionary.word.service.request.SaveWordWithMeaningServiceRequest;
import com.usb.dictionary.word.service.request.SearchWordsServiceRequest;
import com.usb.dictionary.word.service.response.FindWordsServiceResponse;
import com.usb.dictionary.word.service.response.SaveWordWithMeaningServiceResponse;
import com.usb.dictionary.word.service.response.SearchWordsServiceResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

  private static final int PAGE_SIZE = 10;

  private final WordRepository wordRepository;

  private final MeaningRepository meaningRepository;

  private final WordSearchRepository wordSearchRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper;

  @Override
  public SaveWordWithMeaningServiceResponse saveWordWithMeaning(
      SaveWordWithMeaningServiceRequest request) {
    Set<Word> words = request.getWords().stream().map(this::saveWord).collect(Collectors.toSet());
    this.saveMeaning(request.getMeaning(), words);
    this.wordRepository
        .findAllById(words.stream().map(Word::getId).collect(Collectors.toSet()))
        .forEach(this::publishWordModifiedEvent);
    log.info(
        "message=\"words are saved with meaning\", feature=WordServiceImpl, method=saveWordWithMeaning");
    return SaveWordWithMeaningServiceResponse.builder().build();
  }

  @Override
  public FindWordsServiceResponse findWords(FindWordsServiceRequest request) {
    List<Word> words = this.wordRepository.findAllById(request.getWordIds());
    FindWordsServiceResponse response =
        FindWordsServiceResponse.builder()
            .words(
                words.stream()
                    .map(
                        word ->
                            WordDto.builder()
                                .id(word.getId())
                                .content(word.getContent())
                                .languageCode(word.getLanguageCode())
                                .description(word.getDescription())
                                .tags(word.getTags())
                                .build())
                    .collect(Collectors.toSet()))
            .build();
    log.info(
        "message=\"words found: "
            + words
            + ", for request: "
            + request
            + "\", feature=WordServiceImpl, method=findWords");
    return response;
  }

  @Override
  public SearchWordsServiceResponse search(SearchWordsServiceRequest request) {
    Page<WordSearch> result =
        hasText(request.getContent())
            ? searchWordByContent(request.getContent(), request.getPage())
            : hasText(request.getTag())
                ? searchByTag(request.getTag(), request.getPage())
                : searchRandomWords(request.getLanguageCode(), request.getPage());

    Map<Long, Meaning> meanings = findWordMeanings(result);
    SearchWordsServiceResponse response = mapToSearchWordServiceResponse(result, meanings);
    log.info(
        "message=\"found words: "
            + result
            + ", for: "
            + request
            + "\", featue=WordServiceImpl, method=search");
    return response;
  }

  private Page<WordSearch> searchByTag(String tag, int page) {
    return this.wordSearchRepository.searchByTags(tag, PageRequest.of(page, PAGE_SIZE));
  }

  private Page<WordSearch> searchRandomWords(String languageCode, int page) {
    return this.wordSearchRepository.findRandomWords(languageCode, PageRequest.of(page, PAGE_SIZE));
  }

  private Page<WordSearch> searchWordByContent(String content, int page) {
    return this.wordSearchRepository.findByContent(content, PageRequest.of(page, PAGE_SIZE));
  }

  private Map<Long, Meaning> findWordMeanings(Page<WordSearch> result) {
    Set<Long> meaningIds =
        result
            .get()
            .flatMap(wordSearch -> wordSearch.getMeanings().stream())
            .collect(Collectors.toSet());
    Map<Long, Meaning> meanings =
        this.meaningRepository.findAllById(meaningIds).stream()
            .collect(Collectors.toMap(Meaning::getId, identity()));
    return meanings;
  }

  private SearchWordsServiceResponse mapToSearchWordServiceResponse(
      Page<WordSearch> result, Map<Long, Meaning> meanings) {
    SearchWordsServiceResponse response =
        SearchWordsServiceResponse.builder()
            .pageSize(PAGE_SIZE)
            .totalElements(result.getTotalElements())
            .words(
                result
                    .get()
                    .map(
                        wordSearch ->
                            WordWithMeaningsDto.builder()
                                .id(wordSearch.getId())
                                .content(wordSearch.getContent())
                                .languageCode(wordSearch.getLanguageCode())
                                .description(wordSearch.getDescription())
                                .tags(wordSearch.getTags())
                                .meanings(
                                    wordSearch.getMeanings().stream()
                                        .map(
                                            meaningId -> {
                                              var meaning = meanings.get(meaningId);
                                              return MeaningDto.builder()
                                                  .id(meaning.getId())
                                                  .descriptions(meaning.getDescriptions())
                                                  .build();
                                            })
                                        .collect(Collectors.toSet()))
                                .build())
                    .collect(Collectors.toSet()))
            .build();
    return response;
  }

  @KafkaListener(
      topics = {WordModifiedEvent.TOPIC_NAME},
      groupId = "dictionary.word")
  public void wordModifiedEventFired(String wordModifiedEventString) {
    try {
      WordModifiedEvent wordModifiedEvent =
          this.objectMapper.readValue(wordModifiedEventString, WordModifiedEvent.class);
      this.wordSearchRepository.save(
          WordSearch.builder()
              .id(wordModifiedEvent.getId())
              .content(wordModifiedEvent.getContent())
              .languageCode(wordModifiedEvent.getLanguageCode())
              .description(wordModifiedEvent.getDescription())
              .tags(wordModifiedEvent.getTags())
              .meanings(wordModifiedEvent.getMeanings())
              .build());
      log.info(
          "message=\"word modified event consumed: "
              + wordModifiedEvent
              + "\", feature=WordServiceImpl, method=wordModifiedEventFired");
    } catch (JsonProcessingException e) {
      log.error(
          "message=\"an error occured while trying to consume word modified event\", feature=WordServiceImpl, method=wordModifiedEventFired");
    }
  }

  private Meaning saveMeaning(MeaningDto meaningDto, Set<Word> words) {
    Meaning meaning =
        meaningDto.getId() != null
            ? this.meaningRepository
                .findById(meaningDto.getId())
                .orElse(
                    Meaning.builder().descriptions(new HashSet<>()).words(new HashSet<>()).build())
            : Meaning.builder().descriptions(new HashSet<>()).words(new HashSet<>()).build();
    meaning.getDescriptions().addAll(meaningDto.getDescriptions());
    meaning.getWords().addAll(words);
    meaning = this.meaningRepository.save(meaning);
    log.info(
        "message=\"meaning saved, id:\""
            + meaning.getId()
            + ", feature=WordServiceImpl, method=saveMeaning");
    this.publishMeaningModifiedEvent(meaning);
    return meaning;
  }

  private Word saveWord(WordDto wordDto) {
    Word word =
        wordDto.getId() != null
            ? this.wordRepository.findById(wordDto.getId()).orElse(Word.builder().build())
            : Word.builder().build();
    word.setContent(wordDto.getContent());
    word.setDescription(wordDto.getDescription());
    word.setLanguageCode(wordDto.getLanguageCode());
    word.setTags(wordDto.getTags());
    word = this.wordRepository.save(word);
    log.info("message=\"word saved:\"" + word.getId() + ", feature=WordServiceImpl, method=save");
    this.publishWordModifiedEvent(word);
    return word;
  }

  private void publishWordModifiedEvent(Word word) {
    try {
      kafkaTemplate.send(
          WordModifiedEvent.TOPIC_NAME,
          word.getId().toString(),
          this.objectMapper.writeValueAsString(
              WordModifiedEvent.builder()
                  .id(word.getId())
                  .content(word.getContent())
                  .languageCode(word.getLanguageCode())
                  .description(word.getDescription())
                  .tags(word.getTags())
                  .meanings(
                      !isEmpty(word.getMeanings())
                          ? word.getMeanings().stream()
                              .map(Meaning::getId)
                              .collect(Collectors.toSet())
                          : Collections.emptySet())
                  .build()));

      log.info(
          "message=\"word modified event published, id:\""
              + word.getId()
              + ", feature=WordServiceImpl, method=publishWordModifiedEvent");
    } catch (JsonProcessingException e) {
      log.error(
          "message=\"error occured while trying to produce event\", feature=WordServiceImpl, method=publishWordModifiedEvent");
    }
  }

  private void publishMeaningModifiedEvent(Meaning meaning) {
    try {
      kafkaTemplate.send(
          MeaningModifiedEvent.TOPIC_NAME,
          meaning.getId().toString(),
          this.objectMapper.writeValueAsString(
              MeaningModifiedEvent.builder()
                  .id(meaning.getId())
                  .descriptions(meaning.getDescriptions())
                  .words(
                      !isEmpty(meaning.getWords())
                          ? meaning.getWords().stream().map(Word::getId).collect(Collectors.toSet())
                          : Collections.emptySet())
                  .build()));
      log.info(
          "message=\"meaning modified event published, id:\""
              + meaning.getId()
              + ", feature=WordServiceImpl, method=publishMeaningModifiedEvent");
    } catch (JsonProcessingException e) {
      log.error(
          "message=\"error occured while trying to produce event\", feature=WordServiceImpl, method=publishMeaningModifiedEvent");
    }
  }
}
