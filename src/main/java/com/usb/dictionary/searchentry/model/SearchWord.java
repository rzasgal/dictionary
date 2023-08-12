package com.usb.dictionary.searchentry.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchWord implements Serializable {
  private String name;
  private String nameExt;
  private String languageCode;
  private String description;
}
