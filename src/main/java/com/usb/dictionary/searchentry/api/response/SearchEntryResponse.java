package com.usb.dictionary.searchentry.api.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchEntryResponse implements Serializable {
  @Serial private static final long serialVersionUID = 5281555246986984835L;
  private String id;
  private List<SearchWordResponse> words;
  private String type;
  private Set<String> tags;
}
