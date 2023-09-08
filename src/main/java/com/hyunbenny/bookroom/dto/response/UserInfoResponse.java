package com.hyunbenny.bookroom.dto.response;

import com.hyunbenny.bookroom.dto.UserAccountDto;

public record UserInfoResponse(
        String userId,
        String name,
        String nickname,
        String email,
        String phone
) {
    public static UserInfoResponse of(UserAccountDto userAccountDto) {
        return new UserInfoResponse(userAccountDto.userId(), userAccountDto.name(), userAccountDto.nickname(), userAccountDto.email(), userAccountDto.phone());
    }
}
