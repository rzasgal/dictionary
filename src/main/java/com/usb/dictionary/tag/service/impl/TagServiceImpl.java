package com.usb.dictionary.tag.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.tag.model.Tag;
import com.usb.dictionary.tag.repository.mongo.TagRepository;
import com.usb.dictionary.tag.service.TagService;
import com.usb.dictionary.tag.service.mapper.TagServiceMapper;
import com.usb.dictionary.tag.service.request.SaveTagServiceRequest;
import com.usb.dictionary.tag.service.response.GetAllTagsServiceResponse;
import com.usb.dictionary.tag.service.response.SaveTagServiceResponse;
import com.usb.dictionary.word.event.WordModifiedEvent;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
  private final TagRepository tagRepository;
  private final ObjectMapper objectMapper;

  private final TagServiceMapper tagServiceMapper;

  @Override
  public SaveTagServiceResponse save(SaveTagServiceRequest saveTagServiceRequest) {
    Optional<Tag> tagByNameOptional =
        this.tagRepository.findByName(saveTagServiceRequest.getName());
    Tag tag = null;
    if (!tagByNameOptional.isPresent()) {
      Tag newTag =
          Tag.builder().uuid(UUID.randomUUID()).name(saveTagServiceRequest.getName()).build();
      tag = this.tagRepository.save(newTag);
    } else {
      tag = tagByNameOptional.get();
    }
    return SaveTagServiceResponse.builder().uuid(tag.getUuid()).build();
  }

  @Override
  public GetAllTagsServiceResponse getAllTags() {
    return GetAllTagsServiceResponse.builder()
        .tags(this.tagServiceMapper.toTagDto(this.tagRepository.findAll()))
        .build();
  }

  @KafkaListener(
      topics = {EntryModified.TOPIC_NAME},
      groupId = "dictionary.tag")
  private void saveTagsToFullTextSearchRepository(String stringEntryModified) {
    try {
      EntryModified entryModified =
          this.objectMapper.readValue(stringEntryModified, EntryModified.class);
      if (!CollectionUtils.isEmpty(entryModified.getTags())) {
        entryModified
            .getTags()
            .forEach(tag -> this.save(SaveTagServiceRequest.builder().name(tag).build()));
      }
    } catch (Exception exception) {
      log.info(
          "message=\"an error occurred\""
              + ", method=saveTagsToFullTextSearchRepository, feature=TagServiceImpl",
          exception);
    }
  }

  @KafkaListener(
      topics = {WordModifiedEvent.TOPIC_NAME},
      groupId = "dictionary.tag")
  private void saveTagsToFullTextSearchRepositoryFromWordModified(String stringWordModified) {
    try {
      WordModifiedEvent wordModifiedEvent =
          this.objectMapper.readValue(stringWordModified, WordModifiedEvent.class);
      if (!CollectionUtils.isEmpty(wordModifiedEvent.getTags())) {
        wordModifiedEvent
            .getTags()
            .forEach(tag -> this.save(SaveTagServiceRequest.builder().name(tag).build()));
      }
    } catch (Exception exception) {
      log.info(
          "message=\"an error occurred\""
              + ", method=saveTagsToFullTextSearchRepository, feature=TagServiceImpl",
          exception);
    }
  }
}
