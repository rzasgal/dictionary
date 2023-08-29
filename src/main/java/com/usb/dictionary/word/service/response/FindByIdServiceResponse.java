package com.usb.dictionary.word.service.response;

import com.usb.dictionary.word.service.model.MeaningDto;
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
public class FindByIdServiceResponse implements Serializable {

  @Serial private static final long serialVersionUID = -8522020286150901218L;
  private Long id;
  private String content;
  private String description;
  private String languageCode;
  private Set<String> tags;
  private Set<MeaningDto> meanings;
}
