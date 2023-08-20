package com.usb.dictionary.word.api.controller.mapper;

import com.usb.dictionary.word.api.controller.request.AddSentenceRequest;
import com.usb.dictionary.word.api.controller.request.FindByIdsRequest;
import com.usb.dictionary.word.api.controller.request.SaveWordRequest;
import com.usb.dictionary.word.api.controller.request.SearchWordRequest;
import com.usb.dictionary.word.api.controller.response.FindByIdsResponse;
import com.usb.dictionary.word.api.controller.response.SearcResponse;
import com.usb.dictionary.word.service.request.AddSentenceServiceRequest;
import com.usb.dictionary.word.service.request.FindByIdsServiceRequest;
import com.usb.dictionary.word.service.request.SaveServiceRequest;
import com.usb.dictionary.word.service.request.SearchServiceRequest;
import com.usb.dictionary.word.service.response.FindByIdsServiceResponse;
import com.usb.dictionary.word.service.response.SearchServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordControllerMapper {

  SaveServiceRequest toSaveServiceRequest(SaveWordRequest request);

  FindByIdsResponse toFindByIdsResponse(FindByIdsServiceResponse response);

  FindByIdsServiceRequest toFindByIdsServiceRequest(FindByIdsRequest request);

  SearchServiceRequest toSearchServiceRequest(SearchWordRequest request);

  SearcResponse toSearchResponse(SearchServiceResponse search);

  AddSentenceServiceRequest toAddSentenceServiceRequest(AddSentenceRequest request);
}
