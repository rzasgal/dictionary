package com.usb.dictionary.sentence.service.response;

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
public class SaveServiceResponse implements Serializable {
  @Serial private static final long serialVersionUID = 3991619967744518495L;
  private Long id;
}
