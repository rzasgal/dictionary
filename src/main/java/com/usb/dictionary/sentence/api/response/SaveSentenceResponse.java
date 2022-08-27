package com.usb.dictionary.sentence.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveSentenceResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -3414350870662367032L;

    private String id;
}
