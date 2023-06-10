package com.usb.dictionary.sentence.service.mapper;

import com.usb.dictionary.sentence.model.Sentence;
import com.usb.dictionary.sentence.service.response.SentenceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SentenceServiceMapper {
    SentenceDto toSentenceDto(Sentence sentence);
}
