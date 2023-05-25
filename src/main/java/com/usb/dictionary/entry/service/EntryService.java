package com.usb.dictionary.entry.service;

import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;

import java.io.IOException;

public interface EntryService {

    void save(SaveEntryServiceRequest saveEntryServiceRequest);

    void delete(String id);

    void readFromFile() throws IOException;

    void readFromFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException;
}
