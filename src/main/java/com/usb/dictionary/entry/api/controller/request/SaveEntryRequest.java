package com.usb.dictionary.entry.api.controller.request;

import com.usb.dictionary.entry.model.EntryDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class SaveEntryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1536418202348660424L;
    private EntryDto entry;
}
