package com.usb.dictionary.word.service;

import com.usb.dictionary.word.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.word.service.request.AddSentenceServiceRequest;
import com.usb.dictionary.word.service.request.FindByIdsServiceRequest;
import com.usb.dictionary.word.service.request.SaveServiceRequest;
import com.usb.dictionary.word.service.request.SearchServiceRequest;
import com.usb.dictionary.word.service.response.AddSentenceServiceResponse;
import com.usb.dictionary.word.service.response.FindByIdServiceResponse;
import com.usb.dictionary.word.service.response.FindByIdsServiceResponse;
import com.usb.dictionary.word.service.response.FindSentencesServiceResponse;
import com.usb.dictionary.word.service.response.SaveServiceResponse;
import com.usb.dictionary.word.service.response.SearchServiceResponse;
import java.io.IOException;

public interface WordService {

  SaveServiceResponse save(SaveServiceRequest request);

  FindByIdsServiceResponse findByIds(FindByIdsServiceRequest request);

  SearchServiceResponse search(SearchServiceRequest searchServiceRequest);

  AddSentenceServiceResponse addSentence(AddSentenceServiceRequest request);

  void readFromFile(ReadFromXlsxFileServiceRequest request) throws IOException;

  FindByIdServiceResponse findById(Long wordId);

  FindSentencesServiceResponse findSentences(Long wordId);
}
