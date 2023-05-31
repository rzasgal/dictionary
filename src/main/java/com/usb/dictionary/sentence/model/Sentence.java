package com.usb.dictionary.sentence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "sentence")
public class Sentence {

    @Id
    private String id;
    private String content;
    private Set<String> tags;
    private Set<String> entryIds;
    @Version
    private String version;
}
