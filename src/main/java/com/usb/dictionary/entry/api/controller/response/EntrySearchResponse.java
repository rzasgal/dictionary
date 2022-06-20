package com.usb.dictionary.entry.api.controller.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class EntrySearchResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3737118657025004308L;
}
