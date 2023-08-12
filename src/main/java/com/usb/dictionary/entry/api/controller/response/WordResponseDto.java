package com.usb.dictionary.entry.api.controller.response;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordResponseDto implements Serializable {
  @Serial private static final long serialVersionUID = 4679052900240798011L;
  private String name;
  private String languageCode;
  private String description;
}
