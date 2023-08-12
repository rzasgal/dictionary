package com.usb.dictionary.word.api.controller.mapper;

import com.usb.dictionary.word.api.controller.request.FindWordsRequest;
import com.usb.dictionary.word.api.controller.request.SaveWordRequest;
import com.usb.dictionary.word.api.controller.request.SearchWordRequest;
import com.usb.dictionary.word.api.controller.response.FindWordsResponse;
import com.usb.dictionary.word.api.controller.response.SearchWordResponse;
import com.usb.dictionary.word.service.request.FindWordsServiceRequest;
import com.usb.dictionary.word.service.request.SaveWordWithMeaningServiceRequest;
import com.usb.dictionary.word.service.request.SearchWordsServiceRequest;
import com.usb.dictionary.word.service.response.FindWordsServiceResponse;
import com.usb.dictionary.word.service.response.SearchWordsServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordControllerMapper {

  SaveWordWithMeaningServiceRequest toSaveWordWithMeaningServiceRequest(SaveWordRequest request);

  FindWordsResponse toFindWordsResponse(FindWordsServiceResponse response);

  FindWordsServiceRequest toFindWordsServiceRequest(FindWordsRequest request);

  SearchWordsServiceRequest toSearchWordsServiceRequest(SearchWordRequest request);

  SearchWordResponse toSearchWordResponse(SearchWordsServiceResponse search);
}
