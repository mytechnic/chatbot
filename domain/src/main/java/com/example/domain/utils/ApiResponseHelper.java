package com.example.domain.utils;

import com.example.core.exception.ErrorCode;
import com.example.domain.rest.ApiResponse;

public class ApiResponseHelper {

    public static ApiResponse<?> ok() {
        return new ApiResponse<>();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode);
    }
}
