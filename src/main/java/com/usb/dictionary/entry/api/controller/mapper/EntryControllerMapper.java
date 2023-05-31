package com.usb.dictionary.entry.api.controller.mapper;

import com.usb.dictionary.entry.api.controller.request.SaveEntryRequest;
import com.usb.dictionary.entry.api.controller.response.GetEntryResponse;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.entry.service.response.GetEntryServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntryControllerMapper {
    SaveEntryServiceRequest toSaveEntryServiceRequest(SaveEntryRequest saveEntryRequest);

    GetEntryResponse toGetEntryResponse(GetEntryServiceResponse getEntryServiceResponse);
}
