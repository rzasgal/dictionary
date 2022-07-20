package com.usb.dictionary.entry.api.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 5281555246986984835L;
    private String word;
    private String type;
    private String sourceLanguageCode;
    private Map<String, String> translations;
}
