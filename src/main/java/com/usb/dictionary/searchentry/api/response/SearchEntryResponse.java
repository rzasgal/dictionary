package com.usb.dictionary.searchentry.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchEntryResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 5281555246986984835L;
    private String word;
    private String type;
    private String sourceLanguageCode;
    private Map<String, String> translations;
    private Set<String> tags;
}
