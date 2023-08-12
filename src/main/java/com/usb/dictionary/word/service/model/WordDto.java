package com.usb.dictionary.word.service.model;

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
public class WordDto implements Serializable {
  private Long id;
  private String content;
  private String languageCode;
  private String description;
  private Set<String> tags;
}
