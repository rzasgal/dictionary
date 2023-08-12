package com.usb.dictionary.searchentry.response;

import com.usb.dictionary.searchentry.model.SearchEntryDto;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchEntryResult implements Serializable {
  @Serial private static final long serialVersionUID = 8147769026956503542L;
  private List<SearchEntryDto> entries;
}
