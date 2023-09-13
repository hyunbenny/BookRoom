package com.hyunbenny.bookroom.service;

import org.springframework.http.ResponseEntity;

public interface BookSearchClient {

    ResponseEntity getBookInfo(String title, String isbn, int start);
}
