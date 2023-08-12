package com.usb.dictionary.word.service.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "word")
public class WordSearch implements Serializable {

  @Serial private static final long serialVersionUID = -183960536466399487L;

  @Id private Long id;
  private String content;
  private String languageCode;
  private String description;
  private Set<String> tags;
  private Set<Long> meanings;
}
