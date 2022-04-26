package com.example.domain.error;

import com.example.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServiceError implements ErrorCode {
    NOT_FOUND("서비스를 찾을 수 없습니다."),
    DUP_SERVICE_ID("중복된 서비스 아이디입니다."),
    DUP_SERVICE_NAME("중복된 서비스 이름입니다.");

    private final String message;
}
