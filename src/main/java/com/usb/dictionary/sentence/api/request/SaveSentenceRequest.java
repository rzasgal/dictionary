package com.usb.dictionary.sentence.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveSentenceRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 4504918765701954888L;
    private String sentence;
    private Set<String> tags;
}
