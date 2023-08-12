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
public class SearchWordResponseMeaningDto implements Serializable {

  @Serial private static final long serialVersionUID = 402962395608344205L;
  private Long id;
  private Set<String> descriptions;
}
