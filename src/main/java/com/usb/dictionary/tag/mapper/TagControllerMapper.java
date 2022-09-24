package com.usb.dictionary.tag.mapper;

import com.usb.dictionary.tag.api.response.GetAllTagsResponse;
import com.usb.dictionary.tag.service.response.GetAllTagsServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagControllerMapper {
    GetAllTagsResponse toGetAllTagsResponse(GetAllTagsServiceResponse allTags);
}
