package com.usb.dictionary.word.service.response;

import com.usb.dictionary.sentence.service.response.SentenceDto;
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
public class FindSentencesServiceResponse implements Serializable {

  @Serial private static final long serialVersionUID = -7312025208909034014L;
  private Set<SentenceDto> sentences;
}
