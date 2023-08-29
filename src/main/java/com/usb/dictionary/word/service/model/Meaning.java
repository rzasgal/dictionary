package com.usb.dictionary.word.service.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Node(value = "meaning")
public class Meaning implements Serializable {

  @Serial private static final long serialVersionUID = -8957695008400146560L;
  @GeneratedValue @Id private Long id;

  private String type;

  @Property(name = "descriptions")
  private Set<String> descriptions;

  @EqualsAndHashCode.Exclude
  @Relationship(type = "means", direction = Relationship.Direction.INCOMING)
  private Set<Word> words;

  @Version private Long version;
}
