package com.usb.dictionary.entry.api.controller;

import com.usb.dictionary.entry.api.controller.mapper.EntryControllerMapper;
import com.usb.dictionary.entry.api.controller.request.SaveEntryRequest;
import com.usb.dictionary.entry.api.controller.response.SearchEntryResponse;
import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.request.SearchEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

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
        this.entryService.saveCombination(this.entryControllerMapper.toSaveEntryServiceRequest(saveEntryRequest));
        return ResponseEntity.created( ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand("").toUri()).build();
    }


    @PostMapping(path = "/read-from-file")
    public ResponseEntity readFromFile() throws IOException {
        this.entryService.readFromFile();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/upload-file")
    public ResponseEntity uploadFile(@RequestParam("file")MultipartFile file
            , @RequestParam(value = "wordIndex") Integer wordIndex
            , @RequestParam(value = "sourceLanguageCodeIndex", required = false) Integer sourceLanguageCodeIndex
            , @RequestParam(value = "typeIndex") Integer typeIndex
            , @RequestParam(value = "meaningIndex") Integer meaningIndex
            , @RequestParam(value = "targetLanguageCodeIndex", required = false) Integer targetLanguageCodeIndex
            , @RequestParam(value = "sheetIndex", required = false) Integer sheetIndex) throws IOException {
        this.entryService.readFromFile(ReadFromXlsxFileServiceRequest.builder()
                        .resource(file.getInputStream())
                        .wordIndex(wordIndex)
                        .sourceLanguageCodeIndex(sourceLanguageCodeIndex)
                        .typeIndex(typeIndex)
                        .meaningIndex(meaningIndex)
                        .targetLanguageIndex(targetLanguageCodeIndex)
                        .sheetIndex(sheetIndex).build());
        return ResponseEntity.ok().build();
    }
}
