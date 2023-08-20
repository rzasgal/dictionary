package com.usb.dictionary.sentence.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Node(value = "sentence")
public class Sentence {

  @Id @GeneratedValue private Long id;
  private String content;
  private Set<String> tags;
  @Version private String version;
}
