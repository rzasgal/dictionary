package com.usb.dictionary.sentence.service;

import com.usb.dictionary.sentence.service.request.SaveSentenceServiceRequest;
import com.usb.dictionary.sentence.service.response.GetSentencesServiceResponse;
import com.usb.dictionary.sentence.service.response.SaveSentenceServiceResponse;

public interface SentenceService {
    GetSentencesServiceResponse getByEntry(String entryId, int page);

    SaveSentenceServiceResponse save(SaveSentenceServiceRequest saveSentenceServiceRequest);
}
