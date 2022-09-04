package com.usb.dictionary.searchentry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchEntryDto implements Serializable {
    private String id;
    private String word;
    private String type;
    private String sourceLanguageCode;
    private Map<String, String> translations;
    private Set<String> tags;
}
