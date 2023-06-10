package com.usb.dictionary.sentence.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSentencesServiceResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -690122860831874373L;

    private List<SentenceDto> sentences;
}
