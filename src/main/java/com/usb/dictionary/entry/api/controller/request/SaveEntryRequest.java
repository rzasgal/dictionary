package com.usb.dictionary.entry.api.controller.request;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveEntryRequest implements Serializable {
  @Serial private static final long serialVersionUID = -1536418202348660424L;
  private EntryRequestDto entry;
}
