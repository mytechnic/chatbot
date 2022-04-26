package com.example.web.error;

import com.example.core.exception.BusinessException;
import com.example.domain.error.CommonError;
import com.example.domain.rest.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(value = BindException.class)
    public Object bindException(HttpServletRequest request, HttpServletResponse response, BindException exception) {

        log.warn("error: {}, url: [{}] {}", exception.getMessage(), request.getMethod(), request.getRequestURL());
        log.info("", exception);

        BusinessException e = new BusinessException(CommonError.BAD_REQUEST);

        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(c -> ((FieldError) c).getField() + ": " + c.getDefaultMessage())
                .sorted()
                .collect(Collectors.toList());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(new ApiResponse<>(e.getCode(), e.getMessage(), errors), HttpStatus.OK);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public Object businessError(HttpServletRequest request, HttpServletResponse response, BusinessException exception) {

        log.debug("error: {}, url: [{}] {}", exception.getMessage(), request.getMethod(), request.getRequestURL());
        log.debug("", exception);

        return error(response, exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public Object internalServerError(HttpServletRequest request, HttpServletResponse response, Exception exception) {

        log.warn("error: {}, url: [{}] {}", exception.getMessage(), request.getMethod(), request.getRequestURL());
        log.info("", exception);

        return error(response, CommonError.INTERNAL_SERVER_ERROR.name(), CommonError.INTERNAL_SERVER_ERROR.getMessage());
    }

    private ResponseEntity<?> error(HttpServletResponse response, String errorCode, String errorMessage) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(new ApiResponse<>(errorCode, errorMessage), HttpStatus.OK);
    }
}