package com.usb.dictionary.word.service.request;

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
public class FindByIdsServiceRequest implements Serializable {

  @Serial private static final long serialVersionUID = 3522392012149760754L;
  private Set<Long> wordIds;
}
