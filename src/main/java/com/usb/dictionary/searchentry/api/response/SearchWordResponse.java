package com.usb.dictionary.searchentry.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchWordResponse implements Serializable{

    @Serial
    private static final long serialVersionUID = -3264879121621035228L;
    private String name;
    private String languageCode;
    private String description;
    private String type;
}
