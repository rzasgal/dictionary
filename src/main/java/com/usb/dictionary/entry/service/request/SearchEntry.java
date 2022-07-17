package com.usb.dictionary.entry.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchEntry implements Serializable {
    private String word;
    private String sourceLanguageCode;
    private int page;
}
