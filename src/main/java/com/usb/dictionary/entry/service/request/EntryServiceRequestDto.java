package com.usb.dictionary.entry.service.request;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntryServiceRequestDto implements Serializable {
  private String id;
  private List<WordServiceRequestDto> words;
  private String type;
  private Set<String> tags;
}
