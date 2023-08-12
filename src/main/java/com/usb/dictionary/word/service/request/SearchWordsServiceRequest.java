package com.usb.dictionary.word.service.request;

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
public class SearchWordsServiceRequest implements Serializable {

  @Serial private static final long serialVersionUID = 1126710343542226452L;
  private String content;
  private String languageCode;
  private String tag;
  private boolean random;
  private int page;
}
