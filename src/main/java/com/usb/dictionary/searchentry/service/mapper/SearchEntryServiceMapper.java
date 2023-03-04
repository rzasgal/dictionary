package com.usb.dictionary.searchentry.service.mapper;


import com.usb.dictionary.entry.event.EntryModified;
import com.usb.dictionary.entry.model.WordDto;
import com.usb.dictionary.searchentry.model.SearchEntry;
import com.usb.dictionary.searchentry.model.SearchEntryDto;
import com.usb.dictionary.searchentry.model.SearchWord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SearchEntryServiceMapper {

    SearchEntry toSearchEntry(EntryModified entryModified);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "name", target = "nameExt")
    })
    SearchWord toSearchWord(WordDto wordDto);

    SearchEntryDto toSearchEntryDto(SearchEntry searchEntry);

}
