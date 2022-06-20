package com.usb.dictionary.entry.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EntryDto implements Serializable {
    private String word;
    private String type;
    private String sourceLanguageCode;
    private String targetLanguageCode;
    private String meaning;
}
