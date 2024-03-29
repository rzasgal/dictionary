package com.usb.dictionary.word.service.request;

import com.usb.dictionary.word.service.model.MeaningDto;
import com.usb.dictionary.word.service.model.WordDto;
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
public class SaveServiceRequest implements Serializable {
  @Serial private static final long serialVersionUID = -5574679361257260728L;
  private Set<WordDto> words;
  private Set<MeaningDto> meanings;
}
