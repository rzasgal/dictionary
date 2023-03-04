package com.usb.dictionary.entry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document("entry")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

    @Id
    private String id;
    private List<Word> words;
    private String type;
    private Set<String> tags;
    @Version
    private String version;
}
