package com.hyunbenny.bookroom.dto;

import com.hyunbenny.bookroom.entity.Book;

import java.time.LocalDate;

public record BookDto(
        Long id,
        String title,
        String author,
        String isbn,
        String publisher,
        LocalDate pubDate,
        String imgurl
) {

    public static BookDto of(String title, String author, String isbn, String publisher, LocalDate pubDate, String imgurl) {
        return new BookDto(null, title, author, isbn, publisher, pubDate, imgurl);
    }

    public static BookDto of(Long id, String title, String author, String isbn, String publisher, LocalDate pubDate, String imgurl) {
        return new BookDto(id, title, author, isbn, publisher, pubDate, imgurl);
    }

    public static BookDto from(Book entity) {
        return BookDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getIsbn(),
                entity.getPublisher(),
                entity.getPubDate(),
                entity.getImgUrl()
        );
    }

    public Book toEntity() {
        return Book.of(title, author, isbn, publisher, pubDate, imgurl);
    }
}
