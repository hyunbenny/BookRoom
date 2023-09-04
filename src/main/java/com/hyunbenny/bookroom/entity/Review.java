package com.hyunbenny.bookroom.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "userId")
})
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Lob
    @Column(nullable = false)
    private String review;

    @Column(nullable = false)
    private boolean visible;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAccount userAccount;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "bookshelfId")
    private Bookshelf bookshelf;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @Builder
    public Review(String title, String review, boolean visible, LocalDate startDate, LocalDate endDate, UserAccount userAccount, Bookshelf bookshelf, Book book) {
        this.title = title;
        this.review = review;
        this.visible = visible;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userAccount = userAccount;
        this.bookshelf = bookshelf;
        this.book = book;
        this.createdAt = LocalDateTime.now();
    }
}
