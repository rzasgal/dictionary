package com.usb.dictionary.sentence.service.impl;

import com.usb.dictionary.sentence.model.Sentence;
import com.usb.dictionary.sentence.repository.mongo.SentenceMainStorageRepository;
import com.usb.dictionary.sentence.service.SentenceService;
import com.usb.dictionary.sentence.service.request.SaveSentenceServiceRequest;
import com.usb.dictionary.sentence.service.response.SaveSentenceServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentenceServiceImpl implements SentenceService {
    private final SentenceMainStorageRepository sentenceMainStorageRepository;


    @Override
    public SaveSentenceServiceResponse save(SaveSentenceServiceRequest saveSentenceServiceRequest){
        Optional<Sentence> optionalSentence = this.sentenceMainStorageRepository
                .findByContent(saveSentenceServiceRequest.getSentence());
        Sentence sentence = null;
        if(optionalSentence.isPresent()){
            sentence = optionalSentence.get();
        }
        else{
            sentence = Sentence.builder()
                    .content(saveSentenceServiceRequest.getSentence()).build();
        }
        sentence.setTags(saveSentenceServiceRequest.getTags());
        sentence = this.sentenceMainStorageRepository.save(sentence);
        log.info("message=\"sentence:'{}', saved:{}\", method=save, feature=SentenceServiceImpl"
                , saveSentenceServiceRequest.getSentence(), sentence.getId());
        return SaveSentenceServiceResponse.builder()
                .id(sentence.getId())
                .build();
    }


}
