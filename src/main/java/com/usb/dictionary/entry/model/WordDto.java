package com.usb.dictionary.entry.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordDto implements Serializable {
  private String name;
  private String languageCode;
  private String description;
}
