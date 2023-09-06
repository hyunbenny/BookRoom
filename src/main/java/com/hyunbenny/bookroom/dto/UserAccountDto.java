package com.hyunbenny.bookroom.dto;

import com.hyunbenny.bookroom.entity.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        String userId,
        String name,
        String nickname,
        String password,
        String email,
        String phone,
        String role,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static UserAccountDto of(String userId, String name, String nickname, String password, String email, String phone) {
        return new UserAccountDto(userId, name, nickname, password, email, phone, null, null, null);
    }

    public static UserAccountDto of(String userId, String name, String nickname, String password, String email, String phone, String role, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new UserAccountDto(userId, name, nickname, password, email, phone, role, createdAt, modifiedAt);
    }

    public static UserAccountDto from(UserAccount entity) {
        return UserAccountDto.of(
                entity.getUserId(),
                entity.getName(),
                entity.getNickname(),
                null,
                entity.getEmail(),
                entity.getPhone(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
                );
    }

    public UserAccount toEntity(String encodedPassword) {
        return UserAccount.of(userId, name, nickname, encodedPassword, email, phone);
    }
}
