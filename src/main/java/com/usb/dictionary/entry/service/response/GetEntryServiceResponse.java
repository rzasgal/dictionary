package com.usb.dictionary.entry.service.response;


import com.usb.dictionary.entry.model.WordDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEntryServiceResponse {
    private String id;
    private Set<WordDto> words;
    private Set<String> tags;
    private String type;
}
