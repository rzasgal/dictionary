package com.usb.dictionary.word.service.response;

import com.usb.dictionary.word.service.model.WordDto;
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
public class FindWordsServiceResponse implements Serializable {

  @Serial private static final long serialVersionUID = -5872838101948517551L;
  private Set<WordDto> words;
}
