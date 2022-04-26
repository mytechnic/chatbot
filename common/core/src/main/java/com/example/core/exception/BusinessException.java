package com.example.core.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BusinessException extends RuntimeException {
    private final String code;
    private final String message;

    public BusinessException(ErrorCode error) {
        this.code = error.name();
        this.message = error.getMessage();
    }

    public BusinessException(ErrorCode error, Object... args) {
        this.code = error.name();
        this.message = String.format(error.getMessage(), args);
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
