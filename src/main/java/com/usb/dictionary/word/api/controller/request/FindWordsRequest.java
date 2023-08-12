package com.usb.dictionary.word.api.controller.request;

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
public class FindWordsRequest implements Serializable {

  @Serial private static final long serialVersionUID = -6368106012919663268L;

  private Set<Long> wordIds;
}
