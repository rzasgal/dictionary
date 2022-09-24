package com.usb.dictionary.tag.service.mapper;

import com.usb.dictionary.tag.model.Tag;
import com.usb.dictionary.tag.service.response.TagDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagServiceMapper {
    List<TagDto> toTagDto(List<Tag> tags);
}
