package com.usb.dictionary.entry.service.response;

import com.usb.dictionary.entry.model.WordDto;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEntryServiceResponse {
  private String id;
  private Set<WordDto> words;
  private Set<String> tags;
  private String type;
}
