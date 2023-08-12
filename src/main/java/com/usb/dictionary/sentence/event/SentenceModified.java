package com.usb.dictionary.sentence.event;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenceModified implements Serializable {
  public static final String TOPIC_NAME = "sentence.modified";
  @Serial private static final long serialVersionUID = 6594281360612670398L;

  private String id;
  private String content;
  private Set<String> tags;
  private String version;
  private LocalDateTime timestamp;
}
