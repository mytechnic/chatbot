package com.example.domain.error;

import com.example.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConnectUserError implements ErrorCode {
    NOT_FOUND("유저 정보를 찾을 수 없습니다.");

    private final String message;
}
