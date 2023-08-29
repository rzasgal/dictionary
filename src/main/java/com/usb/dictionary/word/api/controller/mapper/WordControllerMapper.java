package com.usb.dictionary.word.api.controller.mapper;

import com.usb.dictionary.word.api.controller.request.AddSentenceRequest;
import com.usb.dictionary.word.api.controller.request.FindByIdsRequest;
import com.usb.dictionary.word.api.controller.request.SaveWordRequest;
import com.usb.dictionary.word.api.controller.response.FindByIdResponse;
import com.usb.dictionary.word.api.controller.response.FindByIdsResponse;
import com.usb.dictionary.word.api.controller.response.FindSentencesResponse;
import com.usb.dictionary.word.api.controller.response.SearcResponse;
import com.usb.dictionary.word.service.request.AddSentenceServiceRequest;
import com.usb.dictionary.word.service.request.FindByIdsServiceRequest;
import com.usb.dictionary.word.service.request.SaveServiceRequest;
import com.usb.dictionary.word.service.response.FindByIdServiceResponse;
import com.usb.dictionary.word.service.response.FindByIdsServiceResponse;
import com.usb.dictionary.word.service.response.FindSentencesServiceResponse;
import com.usb.dictionary.word.service.response.SearchServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordControllerMapper {

  SaveServiceRequest toSaveServiceRequest(SaveWordRequest request);

  FindByIdsResponse toFindByIdsResponse(FindByIdsServiceResponse response);

  FindByIdsServiceRequest toFindByIdsServiceRequest(FindByIdsRequest request);

  SearcResponse toSearchResponse(SearchServiceResponse search);

  AddSentenceServiceRequest toAddSentenceServiceRequest(AddSentenceRequest request);

  FindByIdResponse toFindByIdResponse(FindByIdServiceResponse serviceResponse);

  FindSentencesResponse toFindSentencesResponse(FindSentencesServiceResponse sentences);
}
