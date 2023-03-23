package com.usb.dictionary.searchentry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchWordDto {
    private String name;
    private String languageCode;
    private String description;
}
