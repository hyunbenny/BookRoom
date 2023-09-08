package com.hyunbenny.bookroom.dto.request;

public record UserDeleteRequest(
        String userId,
        String password
) {
}
