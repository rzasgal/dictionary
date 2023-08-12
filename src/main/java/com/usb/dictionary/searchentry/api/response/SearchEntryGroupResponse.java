package com.usb.dictionary.searchentry.api.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchEntryGroupResponse implements Serializable {
  @Serial private static final long serialVersionUID = 3737118657025004308L;
  private List<SearchEntryResponse> entries;
}
