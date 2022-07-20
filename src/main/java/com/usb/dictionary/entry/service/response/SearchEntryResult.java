package com.usb.dictionary.entry.service.response;

import com.usb.dictionary.entry.model.EntryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchEntryResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 8147769026956503542L;
    private List<EntryDto> entries;
    private List<EntryDto> entriesAlternatives;
}
