package com.usb.dictionary.entry.api.controller;

import com.usb.dictionary.entry.api.controller.request.SaveEntryRequest;
import com.usb.dictionary.entry.api.controller.response.EntrySearchResponse;
import com.usb.dictionary.entry.api.controller.response.SaveEntryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/entry")
public class EntryController {

    @GetMapping("/search")
    private ResponseEntity<EntrySearchResponse> searchEntry(
            @RequestParam("word") String word
            , @RequestParam("sourceLanguageCode") String sourceLanguageCode
            , @RequestParam("targetLanguageCode") String targetLanguageCode){
        return ResponseEntity.ok(EntrySearchResponse.builder().build());
    }

    @PostMapping
    private ResponseEntity<SaveEntryResponse> save(@RequestBody SaveEntryRequest saveEntryRequest){
        return ResponseEntity.created( ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand("").toUri())
                .body(SaveEntryResponse.builder().build());
    }
}
