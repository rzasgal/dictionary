package com.usb.dictionary.searchentry.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchEntryRequest implements Serializable {
  private String tag;
  private String word;
  private String sourceLanguageCode;
  private int page;
}
