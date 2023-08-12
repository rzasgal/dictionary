package com.usb.dictionary.entry.api.controller.request;

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
public class EntryRequestDto implements Serializable {
  @Serial private static final long serialVersionUID = -2352764522069428402L;
  private String id;
  private Set<WordRequestDto> words;
  private Set<String> tags;
  private String type;
}
