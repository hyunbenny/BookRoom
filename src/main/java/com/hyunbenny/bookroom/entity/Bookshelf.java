package com.hyunbenny.bookroom.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "userId")
})
@NoArgsConstructor
public class Bookshelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 15)
    private String name;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAccount userAccount;
    private boolean visible;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "bookshelf")
    private List<Review> reviews = new ArrayList<>();


    public Bookshelf(String name, UserAccount userAccount) {
        this.name = name;
        this.userAccount = userAccount;
        this.visible = true;
        this.createdAt = LocalDateTime.now();
    }
}
