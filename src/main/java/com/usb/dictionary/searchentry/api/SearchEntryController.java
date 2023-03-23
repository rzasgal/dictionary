package com.usb.dictionary.searchentry.api;

import com.usb.dictionary.searchentry.api.mapper.SearchEntryControllerMapper;
import com.usb.dictionary.searchentry.api.response.SearchEntryGroupResponse;
import com.usb.dictionary.searchentry.request.SearchEntryRequest;
import com.usb.dictionary.searchentry.service.SearchEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, exposedHeaders = {"Access-Control-Allow-Origin"})
public class SearchEntryController {

    private final SearchEntryControllerMapper searchEntryControllerMapper;
    private final SearchEntryService searchEntryService;

    @GetMapping("/search")
    public ResponseEntity<SearchEntryGroupResponse> searchEntry(
            @RequestParam(value = "tag", required = false) String tag
            , @RequestParam(value = "word", required = false) String word
            , @RequestParam("sourceLanguageCode") String sourceLanguageCode
            , @RequestParam("page")int page){

        return ResponseEntity.ok(this.searchEntryControllerMapper
                .toSearchEntryResponse(this.searchEntryService.search(SearchEntryRequest.builder()
                        .tag(tag)
                        .word(word)
                        .sourceLanguageCode(sourceLanguageCode)
                        .page(page).build())));
    }
}
