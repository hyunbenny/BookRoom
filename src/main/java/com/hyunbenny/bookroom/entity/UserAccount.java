package com.hyunbenny.bookroom.entity;

import com.hyunbenny.bookroom.Roles;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "phone")
})
@NoArgsConstructor
public class UserAccount {

    @Id
    @Column(nullable = false, length = 50, updatable = false)
    private String userId;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String nickname;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(nullable = false, length = 255)
    private String email;
    @Column(nullable = false, length = 11)
    private String phone;
    @Column(nullable = false, length = 15)
    private String role;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @Builder
    public UserAccount(String userId, String name, String nickname, String password, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = Roles.USER.getRole();
        this.createdAt = LocalDateTime.now();
    }
}
