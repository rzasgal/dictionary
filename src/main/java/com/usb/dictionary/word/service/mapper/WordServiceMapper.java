package com.usb.dictionary.word.service.mapper;

import com.usb.dictionary.sentence.model.Sentence;
import com.usb.dictionary.sentence.service.response.SentenceDto;
import com.usb.dictionary.word.service.model.Meaning;
import com.usb.dictionary.word.service.model.MeaningDto;
import com.usb.dictionary.word.service.model.Word;
import com.usb.dictionary.word.service.model.WordDto;
import com.usb.dictionary.word.service.response.FindByIdServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordServiceMapper {
  MeaningDto toMeaningDto(Meaning meaning);

  WordDto toWordDto(Word word);

  FindByIdServiceResponse toFindByIdServiceResponse(Word word);

  SentenceDto toSentenceDto(Sentence sentence);
}
