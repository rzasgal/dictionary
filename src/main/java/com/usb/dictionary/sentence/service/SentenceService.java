package com.usb.dictionary.sentence.service;

import com.usb.dictionary.sentence.service.request.SaveSentenceServiceRequest;
import com.usb.dictionary.sentence.service.response.SaveSentenceServiceResponse;

public interface SentenceService {
    SaveSentenceServiceResponse save(SaveSentenceServiceRequest saveSentenceServiceRequest);
}
