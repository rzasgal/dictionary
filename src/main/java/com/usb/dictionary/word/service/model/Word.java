package com.usb.dictionary.word.service.model;

import com.usb.dictionary.sentence.model.Sentence;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

@Node(value = "Word")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = {"meanings", "sentences"})
public class Word implements Serializable {

  @Serial private static final long serialVersionUID = -2297711407675577742L;

  @Id @GeneratedValue private Long id;
  private String content;
  private String languageCode;
  private String description;
  private Set<String> tags;

  @EqualsAndHashCode.Exclude
  @Relationship(type = "means", direction = Direction.OUTGOING)
  private Set<Meaning> meanings;

  @Relationship(type = "sentence", direction = Direction.OUTGOING)
  private Set<Sentence> sentences;

  @Version private Long version;
}
