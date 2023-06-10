package com.usb.dictionary.sentence.api.response;

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
public class GetSentencesResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -6103718631292007539L;
    private List<SentenceResponse> sentences;
}
