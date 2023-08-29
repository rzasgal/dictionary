package com.usb.dictionary.word.service.model;

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
public class MeaningDto implements Serializable {
  @Serial private static final long serialVersionUID = 6049645150459890142L;
  private Long id;
  private String type;
  private Set<String> descriptions;
}
