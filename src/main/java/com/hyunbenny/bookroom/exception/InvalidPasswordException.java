package com.hyunbenny.bookroom.exception;

public class InvalidPasswordException extends CustomException {

    private static final String MESSAGE = "비밀번호가 올바르지 않습니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

    public InvalidPasswordException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
