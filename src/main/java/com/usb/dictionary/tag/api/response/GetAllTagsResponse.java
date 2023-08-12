package com.usb.dictionary.tag.api.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllTagsResponse implements Serializable {
  @Serial private static final long serialVersionUID = 1164008386395946356L;

  private List<TagResponse> tags;
}
