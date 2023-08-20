package com.usb.dictionary.word.service.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveServiceResponse implements Serializable {
  @Serial private static final long serialVersionUID = -4832627378353654166L;
  private String word;
  private String languageCode;
  private Map<String, String> meanings;
}
