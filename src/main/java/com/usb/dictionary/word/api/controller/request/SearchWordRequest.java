package com.usb.dictionary.word.api.controller.request;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchWordRequest implements Serializable {

  @Serial private static final long serialVersionUID = -1490004590738930601L;
  private String content;
  private String languageCode;
  private String tag;
  private boolean random;
  private int page;
}
