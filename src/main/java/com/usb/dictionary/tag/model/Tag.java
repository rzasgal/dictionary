package com.usb.dictionary.tag.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Node(value = "tag")
public class Tag {
  @Id @GeneratedValue private Long id;
  private UUID uuid;
  private String name;
}
