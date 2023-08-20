package com.usb.dictionary.sentence.api;

import com.usb.dictionary.sentence.api.mapper.SentenceControllerMapper;
import com.usb.dictionary.sentence.api.request.SaveSentenceRequest;
import com.usb.dictionary.sentence.api.response.SaveSentenceResponse;
import com.usb.dictionary.sentence.service.SentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/sentence")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class SentenceController {

  private final SentenceService sentenceService;
  private final SentenceControllerMapper sentenceControllerMapper;

  @PostMapping
  public SaveSentenceResponse save(@RequestBody SaveSentenceRequest saveSentenceRequest) {
    return this.sentenceControllerMapper.toSaveSentenceResponse(
        this.sentenceService.save(
            this.sentenceControllerMapper.toSaveSentenceServiceRequest(saveSentenceRequest)));
  }
}
