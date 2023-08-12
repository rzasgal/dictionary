package com.usb.dictionary.word.event;

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
public class WordModifiedEvent implements Serializable {

  @Serial private static final long serialVersionUID = -5776304485251024042L;
  public static final String TOPIC_NAME = "dictionary.word";
  private Long id;
  private String content;
  private String languageCode;
  private String description;
  private Set<String> tags;
  private Set<Long> meanings;
}
