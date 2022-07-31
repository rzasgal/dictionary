package com.usb.dictionary.searchentry.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchEntryRequest implements Serializable {
    private String word;
    private String sourceLanguageCode;
    private int page;
}
