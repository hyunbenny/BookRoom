package com.hyunbenny.bookroom.service;

import com.hyunbenny.bookroom.dto.response.NaverBookApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class NaverBookSearchClient <T> implements BookSearchClient <T> {

    private static RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${naver.book.url}")
    private String url;
    @Value(value = "${naver.book.client-id}")
    private String XNaverClientId;
    @Value(value = "${naver.book.client-secret}")
    private String XNaverClientSecret;

    /**
     * HTTPS [GET] https://openapi.naver.com/v1/search/book.json
     * params:  query(String, utf-8)
     * display(Integer)
     * start(Integer)
     * sort(String
     * d_title(String, utf-8)
     * d_isbn(String)
     */

    @Override
    public T getBookInfo(String title, String isbn, int start) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", XNaverClientId);
        headers.add("X-Naver-Client-Secret", XNaverClientSecret);

//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("", "");

        HttpEntity<Object> request = new HttpEntity<>(headers);

        String url = createQueryParam(title, isbn, start);
        log.info("url: {}", url);
        ResponseEntity<NaverBookApiResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, NaverBookApiResponse.class);

        log.info("statusCode: {}", responseEntity.getStatusCode());
        log.info("total: {}", responseEntity.getBody().total());
        log.info("body - item: {}", responseEntity.getBody().items());

        return (T) responseEntity.getBody();
    }

    private String createQueryParam(String title, String isbn, int start) {
        StringBuilder sb = new StringBuilder();

        String param = "";
        sb.append(url).append("?");

        if (title.isBlank() && !isbn.isBlank()) {
            param = "d_isbn=";
            sb.append(param).append(isbn);
        } else if (!title.isBlank() && isbn.isBlank()) {
            param = "d_titl=";
            sb.append(param).append(URLEncoder.encode(title, StandardCharsets.UTF_8));
        } else throw new IllegalArgumentException("title과 isbn 둘 중 하나는 필수값입니다.");

        sb.append("&start=").append(start);
        return sb.toString();
    }

}
