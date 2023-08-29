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
public class FindSentencesResponseSentenceDto implements Serializable {

  @Serial private static final long serialVersionUID = -6474492884503628266L;
  private Long id;
  private String content;
  private Set<String> tags;
}
