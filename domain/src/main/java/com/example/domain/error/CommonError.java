package com.example.domain.error;

import com.example.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonError implements ErrorCode {
    INTERNAL_SERVER_ERROR("오류가 발생했습니다. 잠시 후 다시 시도하시기 바랍니다."),
    BAD_REQUEST("입력 값을 확인 해 주세요.");

    private final String message;
}
