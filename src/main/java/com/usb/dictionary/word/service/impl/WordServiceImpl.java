package com.usb.dictionary.word.service.impl;

import static com.usb.dictionary.word.file.WordFileReader.readFromXlsxFile;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.exception.BusinessException;
import com.usb.dictionary.sentence.model.Sentence;
import com.usb.dictionary.sentence.service.SentenceService;
import com.usb.dictionary.sentence.service.response.SentenceDto;
import com.usb.dictionary.word.event.WordModifiedEvent;
import com.usb.dictionary.word.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.word.repository.elasticsearch.WordSearchRepository;
import com.usb.dictionary.word.repository.neo4j.WordRepository;
import com.usb.dictionary.word.service.MeaningService;
import com.usb.dictionary.word.service.WordService;
import com.usb.dictionary.word.service.model.Meaning;
import com.usb.dictionary.word.service.model.MeaningDto;
import com.usb.dictionary.word.service.model.Word;
import com.usb.dictionary.word.service.model.WordDto;
import com.usb.dictionary.word.service.model.WordSearch;
import com.usb.dictionary.word.service.model.WordWithMeaningsDto;
import com.usb.dictionary.word.service.request.AddSentenceServiceRequest;
import com.usb.dictionary.word.service.request.FindByIdsServiceRequest;
import com.usb.dictionary.word.service.request.SaveServiceRequest;
import com.usb.dictionary.word.service.request.SearchServiceRequest;
import com.usb.dictionary.word.service.response.AddSentenceServiceResponse;
import com.usb.dictionary.word.service.response.FindByIdsServiceResponse;
import com.usb.dictionary.word.service.response.SaveServiceResponse;
import com.usb.dictionary.word.service.response.SearchServiceResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

  private static final int PAGE_SIZE = 10;
  private static final int WORD_NOT_FOUND = 1_01;

  private final WordRepository wordRepository;

  private final MeaningService meaningService;

  private final SentenceService sentenceService;

  private final WordSearchRepository wordSearchRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper;

  @Override
  public SaveServiceResponse save(SaveServiceRequest request) {
    Meaning meaning;
    if (request.getMeaning() != null) {
      meaning =
          this.meaningService.save(
              MeaningDto.builder()
                  .id(request.getMeaning().getId())
                  .descriptions(request.getMeaning().getDescriptions())
                  .build());
    } else {
      meaning = null;
    }
    request.getWords().forEach(wordDto -> this.save(wordDto, meaning));
    log.info(
        "message=\"words are saved with meaning\", feature=WordServiceImpl, method=saveWordWithMeaning");
    return SaveServiceResponse.builder().build();
  }

  @Override
  public FindByIdsServiceResponse findByIds(FindByIdsServiceRequest request) {
    List<Word> words = this.wordRepository.findAllById(request.getWordIds());
    FindByIdsServiceResponse response =
        FindByIdsServiceResponse.builder()
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
  public SearchServiceResponse search(SearchServiceRequest request) {
    Page<WordSearch> result =
        hasText(request.getContent())
            ? searchWordByContent(request.getContent(), request.getPage())
            : hasText(request.getTag())
                ? searchByTag(request.getTag(), request.getPage())
                : searchRandomWords(request.getLanguageCode(), request.getPage());

    Map<Long, Meaning> meanings = findWordMeanings(result);
    SearchServiceResponse response = mapToSearchWordServiceResponse(result, meanings);
    log.info(
        "message=\"found words: "
            + result
            + ", for: "
            + request
            + "\", featue=WordServiceImpl, method=search");
    return response;
  }

  @Override
  public AddSentenceServiceResponse addSentence(AddSentenceServiceRequest request) {
    Word word =
        this.wordRepository
            .findById(request.getWordId())
            .orElseThrow(() -> new BusinessException("", WORD_NOT_FOUND));
    Sentence sentence =
        this.sentenceService.save(
            SentenceDto.builder()
                .id(request.getSentence().getId())
                .content(request.getSentence().getContent())
                .tags(request.getSentence().getTags())
                .build());
    word.getSentences().add(sentence);
    word = this.wordRepository.save(word);
    log.info(
        "message=\"sentence :"
            + sentence.getId()
            + ", added to word: "
            + word.getId()
            + "\", feature=WordServiceImpl, method=addSentence");
    return AddSentenceServiceResponse.builder().build();
  }

  @Override
  public void readFromFile(ReadFromXlsxFileServiceRequest request) throws IOException {
    readFromXlsxFile(request).forEach(this::save);
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
    return this.meaningService.findByIds(meaningIds).stream()
        .collect(Collectors.toMap(Meaning::getId, identity()));
  }

  private SearchServiceResponse mapToSearchWordServiceResponse(
      Page<WordSearch> result, Map<Long, Meaning> meanings) {
    SearchServiceResponse response =
        SearchServiceResponse.builder()
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
                                .type(wordSearch.getType())
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

  private Word save(WordDto wordDto, Meaning meaning) {
    Word word =
        wordDto.getId() != null
            ? this.wordRepository
                .findById(wordDto.getId())
                .orElse(Word.builder().meanings(new HashSet<>()).tags(new HashSet<>()).build())
            : this.wordRepository
                .findByContent(wordDto.getContent())
                .orElse(Word.builder().meanings(new HashSet<>()).tags(new HashSet<>()).build());
    word.setContent(wordDto.getContent());
    word.setDescription(wordDto.getDescription());
    word.setLanguageCode(wordDto.getLanguageCode());
    word.setType(wordDto.getType());
    if (wordDto.getTags() != null) {
      if (isEmpty(wordDto.getTags())) {
        word.setTags(emptySet());
      } else {
        word.getTags().addAll(wordDto.getTags());
      }
    }
    if (meaning != null) {
      word.getMeanings().add(meaning);
    }
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
                          : emptySet())
                  .timestamp(ZonedDateTime.now())
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
}
