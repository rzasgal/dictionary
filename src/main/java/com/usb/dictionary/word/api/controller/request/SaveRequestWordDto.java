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
public class SaveRequestWordDto implements Serializable {

  @Serial private static final long serialVersionUID = -8408322078158196663L;
  private Long id;
  private String content;
  private String languageCode;
  private String description;
  private String type;
  private Set<String> tags;
}
