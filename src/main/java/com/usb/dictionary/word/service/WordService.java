package com.usb.dictionary.word.service;

import com.usb.dictionary.word.service.request.FindWordsServiceRequest;
import com.usb.dictionary.word.service.request.SaveWordWithMeaningServiceRequest;
import com.usb.dictionary.word.service.request.SearchWordsServiceRequest;
import com.usb.dictionary.word.service.response.FindWordsServiceResponse;
import com.usb.dictionary.word.service.response.SaveWordWithMeaningServiceResponse;
import com.usb.dictionary.word.service.response.SearchWordsServiceResponse;

public interface WordService {

  SaveWordWithMeaningServiceResponse saveWordWithMeaning(SaveWordWithMeaningServiceRequest request);

  FindWordsServiceResponse findWords(FindWordsServiceRequest request);

  SearchWordsServiceResponse search(SearchWordsServiceRequest searchWordsServiceRequest);
}
