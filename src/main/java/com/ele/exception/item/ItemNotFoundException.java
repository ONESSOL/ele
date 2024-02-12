package com.ele.exception.item;

public class ItemNotFoundException extends RuntimeException {

    private static final String MESSAGE = "해당 상품을 찾을 수 없습니다.";

    public ItemNotFoundException() {
        super(MESSAGE);
    }

    public ItemNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
