package com.usb.dictionary.tag.api;

import com.usb.dictionary.tag.api.response.GetAllTagsResponse;
import com.usb.dictionary.tag.mapper.TagControllerMapper;
import com.usb.dictionary.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@CrossOrigin(
    origins = "http://localhost:3000",
    maxAge = 3600,
    exposedHeaders = {"Access-Control-Allow-Origin"})
public class TagController {

  private final TagService tagService;
  private final TagControllerMapper tagControllerMapper;

  @GetMapping("/all")
  public ResponseEntity<GetAllTagsResponse> getAllTags() {
    return ResponseEntity.ok(
        this.tagControllerMapper.toGetAllTagsResponse(this.tagService.getAllTags()));
  }
}
