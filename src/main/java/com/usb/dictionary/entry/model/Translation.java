package com.usb.dictionary.entry.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Translation implements Serializable {
    private String targetLanguageCode;
    private String meaning;
}
