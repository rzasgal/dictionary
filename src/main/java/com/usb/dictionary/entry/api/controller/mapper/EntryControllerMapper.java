package com.usb.dictionary.entry.api.controller.mapper;

import com.usb.dictionary.entry.api.controller.request.SaveEntryRequest;
import com.usb.dictionary.entry.api.controller.response.SearchEntryResponse;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.entry.service.response.SearchEntryResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntryControllerMapper {
    SaveEntryServiceRequest toSaveEntryServiceRequest(SaveEntryRequest saveEntryRequest);
    SearchEntryResponse toSearchEntryResponse(SearchEntryResult searchEntryResult);
}
