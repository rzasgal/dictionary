package com.usb.dictionary.word.api.controller.response;

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
public class SearcResponse implements Serializable {

  @Serial private static final long serialVersionUID = 6500634941726660792L;

  private Set<SearchWordResponseWordDto> words;
  private Long totalElements;
  private int pageSize;
}
