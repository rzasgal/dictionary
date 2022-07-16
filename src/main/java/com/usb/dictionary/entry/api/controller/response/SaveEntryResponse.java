package com.usb.dictionary.entry.api.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class SaveEntryResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -475348850754159469L;
}
