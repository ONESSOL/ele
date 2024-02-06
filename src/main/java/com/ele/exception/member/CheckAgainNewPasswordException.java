package com.ele.exception.member;

public class CheckAgainNewPasswordException extends RuntimeException {

    private static final String MESSAGE = "비밀번호를 확인해주세요";

    public CheckAgainNewPasswordException() {
        super(MESSAGE);
    }

    public CheckAgainNewPasswordException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
