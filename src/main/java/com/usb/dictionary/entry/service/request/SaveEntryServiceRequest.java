package com.usb.dictionary.entry.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SaveEntryServiceRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8863568559514178586L;
    EntryServiceRequestDto entry;
}
