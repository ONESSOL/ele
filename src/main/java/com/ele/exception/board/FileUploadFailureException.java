package com.ele.exception.board;

public class FileUploadFailureException extends RuntimeException {

    private static final String MESSAGE = "파일 업로드에 실패했습니다.";

    public FileUploadFailureException() {
        super(MESSAGE);
    }

    public FileUploadFailureException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
