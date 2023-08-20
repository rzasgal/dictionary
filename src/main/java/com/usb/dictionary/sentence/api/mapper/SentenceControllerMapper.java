package com.usb.dictionary.sentence.api.mapper;

import com.usb.dictionary.sentence.api.request.SaveSentenceRequest;
import com.usb.dictionary.sentence.api.response.SaveSentenceResponse;
import com.usb.dictionary.sentence.service.request.SaveServiceRequest;
import com.usb.dictionary.sentence.service.response.SaveServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SentenceControllerMapper {

  SaveSentenceResponse toSaveSentenceResponse(SaveServiceResponse saveServiceResponse);

  SaveServiceRequest toSaveSentenceServiceRequest(SaveSentenceRequest saveSentenceRequest);
}
