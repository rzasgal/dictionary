package com.usb.dictionary.entry.api.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntryRequestDto implements Serializable {
    private List<WordRequestDto> words;
    private String type;
    private Set<String> tags;
}
