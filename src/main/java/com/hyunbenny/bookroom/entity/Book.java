package com.hyunbenny.bookroom.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "author"),
        @Index(columnList = "publisher"),
        @Index(columnList = "isbn")
}, uniqueConstraints = @UniqueConstraint(name = "uniq_book_0001", columnNames ="isbn"))
@NoArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false, length = 255)
    private String title;
    @Column(nullable = false, length = 50)
    private String author;
    @Column(nullable = false, length = 13)
    private String isbn;
    @Column(nullable = false, length = 50)
    private String publisher;
    @Column(nullable = false)
    private LocalDate pubDate;
    @Column(nullable = false)
    private String imgUrl;

    private Book(String title, String author, String isbn, String publisher, LocalDate pubDate, String imgUrl) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.imgUrl = imgUrl;
    }

    public static Book of(String title, String author, String isbn, String publisher, LocalDate pubDate, String imgUrl) {
        return new Book(title, author, isbn, publisher, pubDate, imgUrl);
    }
}
