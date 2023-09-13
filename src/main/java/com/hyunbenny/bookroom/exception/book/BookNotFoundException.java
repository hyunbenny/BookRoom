package com.hyunbenny.bookroom.exception.book;

import com.hyunbenny.bookroom.exception.CustomException;

public class BookNotFoundException extends CustomException {

    private static final String MESSAGE = "해당 책이 존재하지 않습니다.";

    public BookNotFoundException() {
        super(MESSAGE);
    }

    public BookNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
