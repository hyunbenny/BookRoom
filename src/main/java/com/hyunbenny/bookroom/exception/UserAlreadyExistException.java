package com.hyunbenny.bookroom.exception;

public class UserAlreadyExistException extends CustomException{
    private static final String MESSAGE = "해당 회원이 이미 존재합니다.";

    public UserAlreadyExistException() {
        super(MESSAGE);
    }

    public UserAlreadyExistException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
