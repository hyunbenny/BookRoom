package com.hyunbenny.bookroom.dto.request;

public record UserPasswordModifyRequest(
        String userId,
        String password,
        String newPassword
) {

}
