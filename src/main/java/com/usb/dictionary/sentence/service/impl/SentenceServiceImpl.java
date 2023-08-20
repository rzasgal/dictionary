package com.usb.dictionary.sentence.service.impl;

import static java.util.Collections.emptySet;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.sentence.event.SentenceModified;
import com.usb.dictionary.sentence.model.Sentence;
import com.usb.dictionary.sentence.repository.neo4j.SentenceRepository;
import com.usb.dictionary.sentence.service.SentenceService;
import com.usb.dictionary.sentence.service.request.SaveServiceRequest;
import com.usb.dictionary.sentence.service.response.SaveServiceResponse;
import com.usb.dictionary.sentence.service.response.SentenceDto;
import java.time.ZonedDateTime;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentenceServiceImpl implements SentenceService {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final SentenceRepository sentenceRepository;

  @Override
  public SaveServiceResponse save(SaveServiceRequest request) {
    return SaveServiceResponse.builder()
        .id(
            this.save(
                    SentenceDto.builder()
                        .content(request.getContent())
                        .tags(request.getTags())
                        .id(request.getId())
                        .build())
                .getId())
        .build();
  }

  @Override
  public Sentence save(SentenceDto sentenceDto) {
    Sentence sentence =
        sentenceDto.getId() != null
            ? this.sentenceRepository
                .findById(sentenceDto.getId())
                .orElse(Sentence.builder().tags(new HashSet<>()).build())
            : Sentence.builder().tags(new HashSet<>()).build();
    sentence.setId(sentenceDto.getId());
    sentence.setContent(sentenceDto.getContent());
    if (sentenceDto.getTags() != null) {
      if (isEmpty(sentenceDto.getTags())) {
        sentence.setTags(emptySet());
      } else {
        sentence.getTags().addAll(sentenceDto.getTags());
      }
    }
    sentence = this.sentenceRepository.save(sentence);
    log.info(
        "message=\"sentence:'{}', saved:{}\", method=save, feature=SentenceServiceImpl",
        sentenceDto.getContent(),
        sentence.getId());
    generateSentenceModifiedEvent(sentence);
    return sentence;
  }

  private void generateSentenceModifiedEvent(Sentence sentence) {
    SentenceModified sentenceModified =
        SentenceModified.builder()
            .id(sentence.getId())
            .version(sentence.getVersion())
            .tags(sentence.getTags())
            .timestamp(ZonedDateTime.now())
            .build();
    try {
      this.kafkaTemplate.send(
          SentenceModified.TOPIC_NAME, this.objectMapper.writeValueAsString(sentenceModified));
    } catch (JsonProcessingException e) {
      log.error("message=\"exception occurred\"", e);
    }
  }
}
