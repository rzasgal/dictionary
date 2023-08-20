package com.usb.dictionary.sentence.service.request;

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
  @Serial private static final long serialVersionUID = -4765459970105690645L;
  private Long id;
  private String content;
  private Set<String> tags;
}
