package com.usb.dictionary.word.api.controller;

import com.usb.dictionary.word.api.controller.mapper.WordControllerMapper;
import com.usb.dictionary.word.api.controller.request.FindWordsRequest;
import com.usb.dictionary.word.api.controller.request.SaveWordRequest;
import com.usb.dictionary.word.api.controller.request.SearchWordRequest;
import com.usb.dictionary.word.api.controller.response.FindWordsResponse;
import com.usb.dictionary.word.api.controller.response.SearchWordResponse;
import com.usb.dictionary.word.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/word")
@RequiredArgsConstructor
public class WordController {

  private final WordService wordService;
  private final WordControllerMapper wordControllerMapper;

  @PostMapping
  @RequestMapping
  public ResponseEntity save(@RequestBody SaveWordRequest request) {
    this.wordService.saveWordWithMeaning(
        this.wordControllerMapper.toSaveWordWithMeaningServiceRequest(request));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/by-ids")
  public ResponseEntity<FindWordsResponse> findWordsByIds(@RequestBody FindWordsRequest request) {
    return ResponseEntity.ok(
        this.wordControllerMapper.toFindWordsResponse(
            this.wordService.findWords(
                this.wordControllerMapper.toFindWordsServiceRequest(request))));
  }

  @GetMapping("/search")
  public ResponseEntity<SearchWordResponse> search(@RequestBody SearchWordRequest request) {
    return ResponseEntity.ok(
        this.wordControllerMapper.toSearchWordResponse(
            this.wordService.search(
                this.wordControllerMapper.toSearchWordsServiceRequest(request))));
  }
}
