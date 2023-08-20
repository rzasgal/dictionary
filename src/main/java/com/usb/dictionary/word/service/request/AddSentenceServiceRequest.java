package com.usb.dictionary.word.service.request;

import com.usb.dictionary.sentence.service.response.SentenceDto;
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
public class AddSentenceServiceRequest implements Serializable {

  @Serial private static final long serialVersionUID = -8937577282312921794L;
  private Long wordId;
  private SentenceDto sentence;
}
