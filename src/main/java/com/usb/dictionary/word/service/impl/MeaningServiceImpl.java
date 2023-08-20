package com.usb.dictionary.word.service.impl;

import static java.util.Collections.emptySet;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.word.repository.neo4j.MeaningRepository;
import com.usb.dictionary.word.service.MeaningService;
import com.usb.dictionary.word.service.event.MeaningModifiedEvent;
import com.usb.dictionary.word.service.model.Meaning;
import com.usb.dictionary.word.service.model.MeaningDto;
import com.usb.dictionary.word.service.model.Word;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeaningServiceImpl implements MeaningService {

  private final MeaningRepository meaningRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper;

  @Override
  public Meaning save(MeaningDto meaningDto) {
    Meaning meaning =
        meaningDto.getId() != null
            ? this.meaningRepository
                .findById(meaningDto.getId())
                .orElse(
                    Meaning.builder().descriptions(new HashSet<>()).words(new HashSet<>()).build())
            : this.meaningRepository
                .findByDescriptions(meaningDto.getDescriptions())
                .orElse(
                    Meaning.builder().descriptions(new HashSet<>()).words(new HashSet<>()).build());
    if (meaningDto.getDescriptions() != null) {
      if (isEmpty(meaningDto.getDescriptions())) {
        meaning.setWords(emptySet());
      } else {
        meaning.getDescriptions().addAll(meaningDto.getDescriptions());
      }
    }
    meaning = this.meaningRepository.save(meaning);
    log.info(
        "message=\"meaning saved, id:\""
            + meaning.getId()
            + ", feature=MeaningServiceImpl, method=saveMeaning");
    this.publishMeaningModifiedEvent(meaning);
    return meaning;
  }

  @Override
  public Set<Meaning> findByIds(Set<Long> meaningIds) {
    return new HashSet<>(this.meaningRepository.findAllById(meaningIds));
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
                          : emptySet())
                  .timestamp(ZonedDateTime.now())
                  .build()));
      log.info(
          "message=\"meaning modified event published, id:\""
              + meaning.getId()
              + ", feature=MeaningServiceImpl, method=publishMeaningModifiedEvent");
    } catch (JsonProcessingException e) {
      log.error(
          "message=\"error occured while trying to produce event\", feature=MeaningServiceImpl, method=publishMeaningModifiedEvent");
    }
  }
}
