package com.usb.dictionary.word.service.response;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveWordServiceResponse implements Serializable {
  @Serial private static final long serialVersionUID = 5315796937267935996L;
  private Long id;
  private boolean created;
}
