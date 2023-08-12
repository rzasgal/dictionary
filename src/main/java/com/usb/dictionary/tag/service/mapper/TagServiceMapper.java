package com.usb.dictionary.tag.service.mapper;

import com.usb.dictionary.tag.model.Tag;
import com.usb.dictionary.tag.service.response.TagDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagServiceMapper {
  List<TagDto> toTagDto(List<Tag> tags);
}
