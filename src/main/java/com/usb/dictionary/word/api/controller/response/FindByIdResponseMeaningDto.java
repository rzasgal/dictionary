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
public class FindByIdResponseMeaningDto implements Serializable {

  @Serial private static final long serialVersionUID = 2656254377198555064L;
  private Long id;
  private String type;
  private Set<String> descriptions;
}
