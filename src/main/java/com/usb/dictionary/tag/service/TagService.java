package com.usb.dictionary.tag.service;

import com.usb.dictionary.tag.service.request.SaveTagServiceRequest;
import com.usb.dictionary.tag.service.response.GetAllTagsServiceResponse;
import com.usb.dictionary.tag.service.response.SaveTagServiceResponse;

public interface TagService {
    SaveTagServiceResponse save(SaveTagServiceRequest saveTagServiceRequest);

    GetAllTagsServiceResponse getAllTags();
}
