package com.hyunbenny.bookroom.service;

public interface BookSearchClient <T> {

     T getBookInfo(String title, String isbn, int start);
}
