package com.usb.dictionary.entry.model;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryDto implements Serializable {
  private String id;
  private Set<String> words;
  private Set<String> tags;
  private String type;
}
