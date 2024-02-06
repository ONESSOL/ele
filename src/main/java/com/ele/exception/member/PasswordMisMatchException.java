package com.ele.exception.member;

public class PasswordMisMatchException extends RuntimeException {

    private static final String MESSAGE = "비밀번호가 틀립니다.";

    public PasswordMisMatchException() {
        super(MESSAGE);
    }

    public PasswordMisMatchException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
