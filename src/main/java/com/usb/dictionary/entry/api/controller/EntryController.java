package com.usb.dictionary.entry.api.controller;

import com.usb.dictionary.entry.api.controller.mapper.EntryControllerMapper;
import com.usb.dictionary.entry.api.controller.request.SaveEntryRequest;
import com.usb.dictionary.entry.api.controller.response.SearchEntryResponse;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.request.SearchEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;
    private final EntryControllerMapper entryControllerMapper;

    @GetMapping("/search")
    public ResponseEntity<SearchEntryResponse> searchEntry(
            @RequestParam("word") String word
            , @RequestParam("sourceLanguageCode") String sourceLanguageCode
            , @RequestParam("page")int page){

        return ResponseEntity.ok(this.entryControllerMapper
                .toSearchEntryResponse(this.entryService.search(SearchEntry.builder()
                                .word(word)
                                .sourceLanguageCode(sourceLanguageCode)
                                .page(page).build())));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody SaveEntryRequest saveEntryRequest){
        this.entryService.save(this.entryControllerMapper.toSaveEntryServiceRequest(saveEntryRequest));
        return ResponseEntity.created( ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand("").toUri()).build();
    }
}
