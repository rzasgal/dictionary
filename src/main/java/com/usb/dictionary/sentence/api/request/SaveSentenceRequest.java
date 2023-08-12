package com.usb.dictionary.sentence.api.request;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveSentenceRequest implements Serializable {
  @Serial private static final long serialVersionUID = 4504918765701954888L;
  private String id;
  private String content;
  private Set<String> tags;
  private Set<String> entryIds;
}
