package com.ele.exception.token;

public class RefreshTokenNotFoundException extends RuntimeException {

    private static final String MESSAGE = "로그인이 필요합니다.";

    public RefreshTokenNotFoundException() {
        super(MESSAGE);
    }

    public RefreshTokenNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
