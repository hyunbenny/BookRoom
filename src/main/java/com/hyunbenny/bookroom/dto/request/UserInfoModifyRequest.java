package com.hyunbenny.bookroom.dto.request;

public record UserInfoModifyRequest(
        String userId,
        String name,
        String nickname,
        String email,
        String phone
) {


}
