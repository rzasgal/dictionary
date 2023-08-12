package com.usb.dictionary.word.service.request;

import com.usb.dictionary.word.service.model.WordDto;
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
public class SaveWordServiceRequest implements Serializable {
  @Serial private static final long serialVersionUID = -6766153844431059004L;
  private WordDto word;
}
