package com.usb.dictionary.entry.service;

import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.entry.service.request.SearchEntry;
import com.usb.dictionary.entry.service.response.SearchEntryResult;

import java.io.IOException;

public interface EntryService {
    SearchEntryResult search(SearchEntry searchEntry);

    void save(SaveEntryServiceRequest saveEntryServiceRequest);

    void readFromFile() throws IOException;
}
