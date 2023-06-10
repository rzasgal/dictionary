package com.usb.dictionary.sentence.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.dictionary.sentence.event.SentenceModified;
import com.usb.dictionary.sentence.model.Sentence;
import com.usb.dictionary.sentence.repository.mongo.SentenceMainStorageRepository;
import com.usb.dictionary.sentence.service.SentenceService;
import com.usb.dictionary.sentence.service.mapper.SentenceServiceMapper;
import com.usb.dictionary.sentence.service.request.SaveSentenceServiceRequest;
import com.usb.dictionary.sentence.service.response.GetSentencesServiceResponse;
import com.usb.dictionary.sentence.service.response.SaveSentenceServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.elasticsearch.common.Strings.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentenceServiceImpl implements SentenceService {

    private static final int pageSize = 10;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final SentenceMainStorageRepository sentenceMainStorageRepository;
    private final SentenceServiceMapper sentenceServiceMapper;

    @Override
    public GetSentencesServiceResponse getByEntry(String entryId, int page) {
        Page<Sentence> sentences = this.sentenceMainStorageRepository.findByEntryIds(Set.of(entryId), PageRequest.of(page, pageSize));
        return  GetSentencesServiceResponse.builder()
                .sentences(sentences.get().map(this.sentenceServiceMapper::toSentenceDto).toList())
                .build();
    }


    @Override
    public SaveSentenceServiceResponse save(SaveSentenceServiceRequest saveSentenceServiceRequest){
        Sentence sentence = Sentence.builder()
                .id(saveSentenceServiceRequest.getId())
                .content(saveSentenceServiceRequest.getContent())
                .tags(saveSentenceServiceRequest.getTags())
                .entryIds(saveSentenceServiceRequest.getEntryIds())
                .build();
        if(hasText(saveSentenceServiceRequest.getId())){
            Optional<Sentence> optionalSentence = this.sentenceMainStorageRepository
                    .findById(saveSentenceServiceRequest.getId());
            if(optionalSentence.isPresent()){
                sentence.setVersion(optionalSentence.get().getVersion());
            }
        }
        sentence = this.sentenceMainStorageRepository.save(sentence);
        log.info("message=\"sentence:'{}', saved:{}\", method=save, feature=SentenceServiceImpl"
                , saveSentenceServiceRequest.getContent(), sentence.getId());
        generateSentenceModifiedEvent(sentence);
        return SaveSentenceServiceResponse.builder()
                .id(sentence.getId())
                .build();
    }

    private void generateSentenceModifiedEvent(Sentence sentence) {
        SentenceModified sentenceModified = SentenceModified.builder()
                .timestamp(LocalDateTime.now())
                .version(sentence.getVersion())
                .tags(sentence.getTags())
                .build();
        try {
            this.kafkaTemplate.send(SentenceModified.TOPIC_NAME, this.objectMapper.writeValueAsString(sentenceModified));
        } catch (JsonProcessingException e) {
            log.error("message=\"exception occurred\"", e);
        }
    }


}
