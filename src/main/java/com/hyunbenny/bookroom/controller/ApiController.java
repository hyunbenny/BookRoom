package com.hyunbenny.bookroom.controller;

import com.hyunbenny.bookroom.dto.response.NaverBookApiResponse;
import com.hyunbenny.bookroom.service.BookSearchClient;
import com.hyunbenny.bookroom.service.NaverBookSearchClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ApiController {

    private final BookSearchClient bookSearchClient;

    @GetMapping("/book/info/naver")
    public ResponseEntity getBookInfo(@RequestParam String title,
                                                            @RequestParam String isbn,
                                                            @RequestParam(defaultValue = "1") int page) {
        // page 1 -> start = 1
        // page 2 -> start = 11
        // page 3 -> start = 21
        int start = page * 10 - 9;
        NaverBookApiResponse bookInfo = (NaverBookApiResponse) bookSearchClient.getBookInfo(title, isbn, start);

        return ResponseEntity.ok(bookInfo);
    }

}
