package com.usb.dictionary.entry.api.controller;

import com.usb.dictionary.entry.api.controller.mapper.EntryControllerMapper;
import com.usb.dictionary.entry.api.controller.request.SaveEntryRequest;
import com.usb.dictionary.entry.api.controller.response.EntrySearchResponse;
import com.usb.dictionary.entry.service.EntryService;
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

    @GetMapping
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("/search")
    public ResponseEntity<EntrySearchResponse> searchEntry(
            @RequestParam("word") String word
            , @RequestParam("sourceLanguageCode") String sourceLanguageCode
            , @RequestParam("targetLanguageCode") String targetLanguageCode){
        return ResponseEntity.ok(EntrySearchResponse.builder().build());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody SaveEntryRequest saveEntryRequest){
        this.entryService.save(this.entryControllerMapper.toSaveEntryServiceRequest(saveEntryRequest));
        return ResponseEntity.created( ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand("").toUri()).build();
    }
}
