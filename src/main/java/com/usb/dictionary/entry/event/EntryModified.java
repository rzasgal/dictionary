package com.usb.dictionary.entry.event;

import com.usb.dictionary.entry.model.WordDto;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryModified implements Serializable {
  public static final String TOPIC_NAME = "dictionary.entry.modified";
  @Serial private static final long serialVersionUID = -7119920639022950266L;
  private String id;
  private List<WordDto> words;
  private Set<String> tags;
  private String type;
  private String version;
  private LocalDateTime timestamp;
  private boolean deleted = false;
}
