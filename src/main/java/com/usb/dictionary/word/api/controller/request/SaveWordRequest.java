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
public class SaveWordRequest implements Serializable {

  @Serial private static final long serialVersionUID = 1050633394682162639L;
  private Set<SaveRequestWordDto> words;
  private Set<SaveRequestMeaningDto> meanings;
}
