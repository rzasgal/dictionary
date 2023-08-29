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
public class FindByIdResponse implements Serializable {

  @Serial private static final long serialVersionUID = -7514241857769918306L;

  private Long id;
  private String content;
  private String description;
  private String languageCode;
  private Set<String> tags;
  private Set<FindByIdResponseMeaningDto> meanings;
}
