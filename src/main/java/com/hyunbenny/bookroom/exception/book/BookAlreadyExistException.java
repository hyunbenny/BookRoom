package com.hyunbenny.bookroom.exception.book;

import com.hyunbenny.bookroom.exception.CustomException;

public class BookAlreadyExistException extends CustomException {
    private static final String MESSAGE = "해당 책이 이미 존재합니다.";

    public BookAlreadyExistException() {
        super(MESSAGE);
    }

    public BookAlreadyExistException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
