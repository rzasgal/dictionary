package com.usb.dictionary.sentence.service;

import com.usb.dictionary.sentence.model.Sentence;
import com.usb.dictionary.sentence.service.request.SaveServiceRequest;
import com.usb.dictionary.sentence.service.response.SaveServiceResponse;
import com.usb.dictionary.sentence.service.response.SentenceDto;

public interface SentenceService {

  SaveServiceResponse save(SaveServiceRequest saveServiceRequest);

  Sentence save(SentenceDto sentenceDto);
}
