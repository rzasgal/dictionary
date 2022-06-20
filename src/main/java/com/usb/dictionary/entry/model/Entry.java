package com.usb.dictionary.entry.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "word")
public class Entry {

    @Id
    private String word;
    private String type;
    private String sourceLanguageCode;
    private String targetLanguageCode;
    private String meaning;
}
