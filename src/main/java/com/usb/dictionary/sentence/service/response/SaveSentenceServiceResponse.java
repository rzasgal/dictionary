package com.usb.dictionary.sentence.service.response;

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
public class SaveSentenceServiceResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3991619967744518495L;
    private String id;
}
