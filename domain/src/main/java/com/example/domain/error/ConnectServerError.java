package com.example.domain.error;

import com.example.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConnectServerError implements ErrorCode {
    NOT_FOUND("서버 연결 정보를 찾을 수 없습니다.");

    private final String message;
}
