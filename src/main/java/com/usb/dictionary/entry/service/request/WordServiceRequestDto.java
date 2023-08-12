package com.usb.dictionary.entry.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordServiceRequestDto {
  private String name;
  private String languageCode;
  private String description;
}
