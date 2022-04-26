package com.example.domain.error;

import com.example.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebSocketError implements ErrorCode {
    NOT_FOUND_UID("고객정보를 찾을 수 없습니다."),
    NOT_SUPPORT_API("API를 찾을 수 없습니다.");

    private final String message;
}
