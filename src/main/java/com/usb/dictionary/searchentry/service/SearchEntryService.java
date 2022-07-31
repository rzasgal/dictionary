package com.usb.dictionary.searchentry.service;

import com.usb.dictionary.searchentry.request.SearchEntryRequest;
import com.usb.dictionary.searchentry.response.SearchEntryResult;

public interface SearchEntryService {
    SearchEntryResult search(SearchEntryRequest searchEntry);
}
