package com.usb.dictionary.word.service.response;

import com.usb.dictionary.word.service.model.WordDto;
import com.usb.dictionary.word.service.model.WordWithMeaningsDto;
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
public class SearchWordsServiceResponse implements Serializable {

  @Serial private static final long serialVersionUID = -6780361357402638021L;
  private Set<WordWithMeaningsDto> words;
  private Long totalElements;
  private int pageSize;
}
