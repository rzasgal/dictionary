package com.usb.dictionary.entry.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("entry")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

  @Id private String id;
  private Set<Word> words;
  private Set<String> tags;
  private String type;
  @Version private String version;
}
