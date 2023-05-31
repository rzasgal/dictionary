package com.usb.dictionary.entry.service.mapper;


import com.usb.dictionary.entry.model.Entry;
import com.usb.dictionary.entry.model.WordDto;
import com.usb.dictionary.entry.model.Word;
import com.usb.dictionary.entry.service.request.WordServiceRequestDto;
import com.usb.dictionary.entry.service.response.GetEntryServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntryServiceMapper {

    Word toWord(WordServiceRequestDto wordServiceRequestDto);

    WordDto toWordDto(Word word);

    GetEntryServiceResponse toGetEntryServiceResponse(Entry entry);
}
