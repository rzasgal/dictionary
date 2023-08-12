package com.usb.dictionary.entry.api.controller.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEntryResponse implements Serializable {
  @Serial private static final long serialVersionUID = 2154055225891261980L;
  private String id;
  private Set<WordResponseDto> words;
  private Set<String> tags;
  private String type;
}
