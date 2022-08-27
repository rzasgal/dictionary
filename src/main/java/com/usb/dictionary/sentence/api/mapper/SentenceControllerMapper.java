package com.usb.dictionary.sentence.api.mapper;

import com.usb.dictionary.sentence.api.request.SaveSentenceRequest;
import com.usb.dictionary.sentence.api.response.SaveSentenceResponse;
import com.usb.dictionary.sentence.service.request.SaveSentenceServiceRequest;
import com.usb.dictionary.sentence.service.response.SaveSentenceServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SentenceControllerMapper {

    SaveSentenceResponse toSaveSentenceResponse(SaveSentenceServiceResponse saveSentenceServiceResponse);

    SaveSentenceServiceRequest toSaveSentenceServiceRequest(SaveSentenceRequest saveSentenceRequest);
}
