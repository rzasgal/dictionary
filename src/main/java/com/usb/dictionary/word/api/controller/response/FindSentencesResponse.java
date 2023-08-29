package com.usb.dictionary.word.api.controller.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindSentencesResponse implements Serializable {

  @Serial private static final long serialVersionUID = 3641123492386725897L;
  private Set<FindSentencesResponseSentenceDto> sentences;
}
