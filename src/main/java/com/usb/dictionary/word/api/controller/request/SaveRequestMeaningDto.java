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
public class SaveRequestMeaningDto implements Serializable {

  @Serial private static final long serialVersionUID = 7377835145943035000L;
  private Long id;
  private Set<String> descriptions;
}
