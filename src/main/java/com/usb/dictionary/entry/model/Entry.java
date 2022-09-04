package com.usb.dictionary.entry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document(collection = "word")
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
    private Set<String> tags;
}
