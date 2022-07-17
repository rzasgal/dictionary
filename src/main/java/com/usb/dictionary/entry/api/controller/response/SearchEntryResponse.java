package com.usb.dictionary.entry.api.controller.response;

import com.usb.dictionary.entry.model.EntryDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SearchEntryResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3737118657025004308L;
    private List<EntryDto> entries;
}
