package com.hyunbenny.bookroom.dto.response;

import com.hyunbenny.bookroom.dto.NaverBookInfoDto;

import java.util.List;

public record NaverBookApiResponse(
        int total,
        int start,
        List<NaverBookInfoDto> items
) {
}
