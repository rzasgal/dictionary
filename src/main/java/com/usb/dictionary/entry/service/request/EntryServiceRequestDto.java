package com.usb.dictionary.entry.service.request;

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
public class EntryServiceRequestDto implements Serializable {
    private List<WordServiceRequestDto> words;
    private String type;
    private Set<String> tags;
}
