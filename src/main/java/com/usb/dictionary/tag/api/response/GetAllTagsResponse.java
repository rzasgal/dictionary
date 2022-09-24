package com.usb.dictionary.tag.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllTagsResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1164008386395946356L;

    private List<TagResponse> tags;
}
