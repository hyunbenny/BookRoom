package com.hyunbenny.bookroom.exception;

public class UserNotFoundException extends CustomException {

    private static final String MESSAGE = "해당 회원이 존재하지 않습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
