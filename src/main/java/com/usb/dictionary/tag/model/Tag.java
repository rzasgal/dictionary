package com.usb.dictionary.tag.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
  @Id private String id;
  private UUID uuid;
  private String name;
}
