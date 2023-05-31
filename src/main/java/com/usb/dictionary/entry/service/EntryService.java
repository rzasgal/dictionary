package com.usb.dictionary.entry.service;

import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.entry.service.response.GetEntryServiceResponse;

import java.io.IOException;
import java.util.Optional;

public interface EntryService {

    Optional<GetEntryServiceResponse> get(String id);

    void save(SaveEntryServiceRequest saveEntryServiceRequest);

    void delete(String id);

    void readFromFile() throws IOException;

    void readFromFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException;
}
