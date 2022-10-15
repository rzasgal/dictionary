package com.usb.dictionary.entry.api.controller.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class SaveEntryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1536418202348660424L;
    private String word;
    private String type;
    private String sourceLanguageCode;
    private Map<String, String> translations;
    private Set<String> tags;
    private Boolean createCombinations;
}
