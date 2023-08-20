package com.usb.dictionary.word.api.controller.request;

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
public class AddSentenceRequestSentenceDto implements Serializable {

  @Serial private static final long serialVersionUID = 8101820132701527371L;
  private Long id;
  private String content;
  private Set<String> tags;
}
