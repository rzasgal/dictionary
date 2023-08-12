package com.usb.dictionary.word.service.event;

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
public class MeaningModifiedEvent implements Serializable {

  public static final String TOPIC_NAME = "dictionary.meaning";
  @Serial private static final long serialVersionUID = 4977166007295570737L;
  private Long id;
  private Set<String> descriptions;
  private Set<Long> words;
}
