package com.usb.dictionary.word.file.request;

import java.io.InputStream;
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
public class ReadFromXlsxFileServiceRequest implements Serializable {
  @Serial private static final long serialVersionUID = -5011150936877406499L;
  private InputStream resource;
  private String fileName;
  private Integer wordIndex;
  private Integer sourceLanguageCodeIndex;
  private Integer typeIndex;
  private Integer meaningIndex;
  private Integer targetLanguageIndex;
  private Integer sheetIndex;
}
