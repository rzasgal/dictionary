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
public class FindWordsResponseWordDto implements Serializable {

  @Serial private static final long serialVersionUID = -2741225362790137855L;
  private Long id;
  private String content;
  private String languageCode;
  private String description;
  private Set<String> tags;
}
