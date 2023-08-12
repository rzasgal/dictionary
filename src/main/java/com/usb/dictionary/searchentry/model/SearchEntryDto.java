package com.usb.dictionary.searchentry.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchEntryDto implements Serializable {
  private String id;
  private List<SearchWordDto> words;
  private String type;
  private Set<String> tags;
}
