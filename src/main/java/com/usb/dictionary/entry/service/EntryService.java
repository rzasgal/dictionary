package com.usb.dictionary.entry.service;

import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;

public interface EntryService {
    void save(SaveEntryServiceRequest saveEntryServiceRequest);
}
