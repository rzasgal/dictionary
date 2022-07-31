package com.usb.dictionary.searchentry.api;

import com.usb.dictionary.searchentry.api.mapper.SearchEntryControllerMapper;
import com.usb.dictionary.searchentry.api.response.SearchEntryGroupResponse;
import com.usb.dictionary.searchentry.request.SearchEntryRequest;
import com.usb.dictionary.searchentry.service.SearchEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
public class SearchEntryController {

    private final SearchEntryControllerMapper searchEntryControllerMapper;
    private final SearchEntryService searchEntryService;

    @GetMapping("/search")
    public ResponseEntity<SearchEntryGroupResponse> searchEntry(
            @RequestParam("word") String word
            , @RequestParam("sourceLanguageCode") String sourceLanguageCode
            , @RequestParam("page")int page){

        return ResponseEntity.ok(this.searchEntryControllerMapper
                .toSearchEntryResponse(this.searchEntryService.search(SearchEntryRequest.builder()
                        .word(word)
                        .sourceLanguageCode(sourceLanguageCode)
                        .page(page).build())));
    }
}
