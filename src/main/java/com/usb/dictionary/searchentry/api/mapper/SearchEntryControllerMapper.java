package com.usb.dictionary.searchentry.api.mapper;

import com.usb.dictionary.searchentry.api.response.SearchEntryGroupResponse;
import com.usb.dictionary.searchentry.response.SearchEntryResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchEntryControllerMapper {
    SearchEntryGroupResponse toSearchEntryResponse(SearchEntryResult searchEntryResult);
}
