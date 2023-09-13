package com.hyunbenny.bookroom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record NaverBookInfoDto(
        @JsonProperty("title") // 제목
        String title,
        @JsonProperty("image") // 섬네일 이미지 url
        String imgUrl,
        @JsonProperty("author") // 저자 이름
        String author,
        @JsonProperty("publisher") // 출판사
        String publisher,
        @JsonProperty("isbn") // ISBN
        String isbn,
        @JsonProperty("pubdate") // 출간일
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        LocalDate pubDate
) {

}
