package com.usb.dictionary.entry.api.controller;

import com.usb.dictionary.entry.api.controller.mapper.EntryControllerMapper;
import com.usb.dictionary.entry.api.controller.request.SaveEntryRequest;
import com.usb.dictionary.entry.api.controller.response.GetEntryResponse;
import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.service.EntryService;
import com.usb.dictionary.entry.service.response.GetEntryServiceResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class EntryController {

  private final EntryService entryService;
  private final EntryControllerMapper entryControllerMapper;

  @PostMapping
  public ResponseEntity save(@RequestBody SaveEntryRequest saveEntryRequest) {
    this.entryService.save(this.entryControllerMapper.toSaveEntryServiceRequest(saveEntryRequest));
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand("")
                .toUri())
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetEntryResponse> get(@PathVariable("id") String id) {
    Optional<GetEntryServiceResponse> getEntryServiceResponse = this.entryService.get(id);
    if (getEntryServiceResponse.isPresent()) {
      return ResponseEntity.ok(
          this.entryControllerMapper.toGetEntryResponse(getEntryServiceResponse.get()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity delete(@PathVariable("id") String id) {
    this.entryService.delete(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "/read-from-file")
  public ResponseEntity readFromFile() throws IOException {
    this.entryService.readFromFile();
    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "/upload-file")
  public ResponseEntity uploadFile(
      @RequestParam("file") MultipartFile file,
      @RequestParam(value = "wordIndex") Integer wordIndex,
      @RequestParam(value = "sourceLanguageCodeIndex", required = false)
          Integer sourceLanguageCodeIndex,
      @RequestParam(value = "typeIndex") Integer typeIndex,
      @RequestParam(value = "meaningIndex") Integer meaningIndex,
      @RequestParam(value = "targetLanguageCodeIndex", required = false)
          Integer targetLanguageCodeIndex,
      @RequestParam(value = "sheetIndex", required = false) Integer sheetIndex)
      throws IOException {
    this.entryService.readFromFile(
        ReadFromXlsxFileServiceRequest.builder()
            .resource(file.getInputStream())
            .wordIndex(wordIndex)
            .sourceLanguageCodeIndex(sourceLanguageCodeIndex)
            .typeIndex(typeIndex)
            .meaningIndex(meaningIndex)
            .targetLanguageIndex(targetLanguageCodeIndex)
            .sheetIndex(sheetIndex)
            .build());
    return ResponseEntity.ok().build();
  }
}
