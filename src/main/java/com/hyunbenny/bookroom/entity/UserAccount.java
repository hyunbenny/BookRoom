package com.hyunbenny.bookroom.entity;

import com.hyunbenny.bookroom.common.Roles;
import com.hyunbenny.bookroom.dto.UserAccountDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "phone")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SQLDelete(sql = "update \"user_account\" set deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at IS NULL")
public class UserAccount {

    @Id
    @Column(name = "user_id", nullable = false, length = 50, updatable = false)
    private String userId;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Column(name = "phone", nullable = false, length = 11)
    private String phone;
    @Column(name = "role", nullable = false, length = 15)
    private String role;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private UserAccount(String userId, String name, String nickname, String password, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = Roles.USER.getRole();
        this.createdAt = LocalDateTime.now();
    }

    public static UserAccount of(String userId, String name, String nickname, String password, String email, String phone) {
        return new UserAccount(userId, name, nickname, password, email, phone);
    }

    public void modifyUserInfo(UserAccountDto userAccountDto) {
        this.name = userAccountDto.name();
        this.nickname = userAccountDto.nickname();
        this.email = userAccountDto.email();
        this.phone = userAccountDto.phone();
        this.modifiedAt = LocalDateTime.now();
    }

    public void modifyPassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }

    public void deleteUser() {
        this.deletedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

}
