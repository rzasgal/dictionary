package com.usb.dictionary.word.api.controller.response;

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
public class SearchWordResponseWordDto implements Serializable {
  private Long id;
  private String content;
  private String languageCode;
  private String description;
  private Set<String> tags;
  private Set<SearchWordResponseMeaningDto> meanings;
}
