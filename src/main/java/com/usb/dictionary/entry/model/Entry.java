package com.usb.dictionary.entry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Document(indexName = "word")
@org.springframework.data.mongodb.core.mapping.Document(collection = "word")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entry{

    @Id
    private String id;
    private String word;
    private String type;
    private String sourceLanguageCode;
    private List<Translation> translations;
}
