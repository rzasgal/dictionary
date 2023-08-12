package com.usb.dictionary.sentence.service.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentenceDto implements Serializable {
  @Serial private static final long serialVersionUID = 1017422773375496663L;
  private String id;
  private String content;
  private Set<String> tags;
  private Set<String> entryIds;
}
