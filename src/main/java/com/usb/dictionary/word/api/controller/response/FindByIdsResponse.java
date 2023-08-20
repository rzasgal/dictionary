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
public class FindByIdsResponse implements Serializable {

  @Serial private static final long serialVersionUID = -6877645256507340960L;

  private Set<FindWordsResponseWordDto> words;
}
