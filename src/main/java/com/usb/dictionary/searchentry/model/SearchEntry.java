package com.usb.dictionary.searchentry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Set;

@Document(indexName = "entry")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchEntry {
    @Id
    private String id;
    private List<SearchWord> words;
    private Set<String> tags;
}
