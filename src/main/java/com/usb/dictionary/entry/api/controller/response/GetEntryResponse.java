package com.usb.dictionary.entry.api.controller.response;


import com.usb.dictionary.entry.api.controller.request.WordRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEntryResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2154055225891261980L;
    private String id;
    private Set<WordResponseDto> words;
    private Set<String> tags;
    private String type;
}
