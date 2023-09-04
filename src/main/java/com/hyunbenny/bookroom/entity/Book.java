package com.hyunbenny.bookroom.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "author"),
        @Index(columnList = "publisher"),
        @Index(columnList = "isbn")
})
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
    private LocalDateTime pubDate;

    @Builder
    public Book(String title, String author, String isbn, String publisher, LocalDateTime pubDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.pubDate = pubDate;
    }
}
