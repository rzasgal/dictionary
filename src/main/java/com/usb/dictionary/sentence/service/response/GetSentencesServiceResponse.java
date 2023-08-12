package com.usb.dictionary.sentence.service.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSentencesServiceResponse implements Serializable {
  @Serial private static final long serialVersionUID = -690122860831874373L;

  private List<SentenceDto> sentences;
}
