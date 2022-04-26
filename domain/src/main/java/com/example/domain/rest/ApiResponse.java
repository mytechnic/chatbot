package com.example.domain.rest;

import com.example.core.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ApiResponse<T> {
    private final String OK_MESSAGE = "OK";

    @Getter
    @Schema(description = "결과 코드")
    private final String code;

    @Getter
    @Schema(description = "메시지")
    private String message;

    @Getter
    @Schema(description = "결과 데이터")
    private T result;

    @Getter
    @Schema(description = "상세 오류 메시지")
    private List<String> errors;

    public ApiResponse() {
        this.code = OK_MESSAGE;
    }

    public ApiResponse(T result) {
        this();
        this.result = result;
    }

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(String code, String message, List<String> errors) {
        this(code, message);
        this.errors = errors;
    }

    public ApiResponse(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }

    @JsonIgnore
    public boolean isOk() {
        return OK_MESSAGE.equals(code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !OK_MESSAGE.equals(code);
    }
}