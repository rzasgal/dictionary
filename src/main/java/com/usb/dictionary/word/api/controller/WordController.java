package com.usb.dictionary.word.api.controller;

import com.usb.dictionary.word.api.controller.mapper.WordControllerMapper;
import com.usb.dictionary.word.api.controller.request.AddSentenceRequest;
import com.usb.dictionary.word.api.controller.request.FindByIdsRequest;
import com.usb.dictionary.word.api.controller.request.SaveWordRequest;
import com.usb.dictionary.word.api.controller.request.SearchWordRequest;
import com.usb.dictionary.word.api.controller.response.FindByIdsResponse;
import com.usb.dictionary.word.api.controller.response.SearcResponse;
import com.usb.dictionary.word.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.word.service.WordService;
import com.usb.dictionary.word.service.request.AddSentenceServiceRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/word")
@RequiredArgsConstructor
public class WordController {

  private final WordService wordService;
  private final WordControllerMapper wordControllerMapper;

  @PostMapping
  @RequestMapping
  public ResponseEntity saveWithMeaning(@RequestBody SaveWordRequest request) {
    this.wordService.save(this.wordControllerMapper.toSaveServiceRequest(request));
    return ResponseEntity.ok().build();
  }

  @PatchMapping
  @RequestMapping(path = "/{wordId}/sentence")
  public ResponseEntity addSentence(
      @PathVariable(value = "wordId") Long wordId, @RequestBody AddSentenceRequest request) {
    AddSentenceServiceRequest req = this.wordControllerMapper.toAddSentenceServiceRequest(request);
    req.setWordId(wordId);
    this.wordService.addSentence(req);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/by-ids")
  public ResponseEntity<FindByIdsResponse> findWordsByIds(@RequestBody FindByIdsRequest request) {
    return ResponseEntity.ok(
        this.wordControllerMapper.toFindByIdsResponse(
            this.wordService.findByIds(
                this.wordControllerMapper.toFindByIdsServiceRequest(request))));
  }

  @GetMapping("/search")
  public ResponseEntity<SearcResponse> search(@RequestBody SearchWordRequest request) {
    return ResponseEntity.ok(
        this.wordControllerMapper.toSearchResponse(
            this.wordService.search(this.wordControllerMapper.toSearchServiceRequest(request))));
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
    this.wordService.readFromFile(
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
