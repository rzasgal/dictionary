package com.usb.dictionary.word.api.controller.request;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddSentenceRequest implements Serializable {

  @Serial private static final long serialVersionUID = 4966424300345018622L;
  private Long wordId;
  private AddSentenceRequestSentenceDto sentence;
}
