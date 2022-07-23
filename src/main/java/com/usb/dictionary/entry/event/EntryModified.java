package com.usb.dictionary.entry.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryModified implements Serializable {
    public static final String TOPIC_NAME = "entry.modified";
    @Serial
    private static final long serialVersionUID = -7119920639022950266L;
    private String word;
    private String type;
    private String sourceLanguageCode;
    private Map<String, String> translations;
}
