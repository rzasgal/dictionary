package com.usb.dictionary.word.service;

import com.usb.dictionary.word.service.model.Meaning;
import com.usb.dictionary.word.service.model.MeaningDto;
import java.util.Set;

public interface MeaningService {

  Meaning save(MeaningDto meaningDto);

  Set<Meaning> findByIds(Set<Long> meaningIds);
}
