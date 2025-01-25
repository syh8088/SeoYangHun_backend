package com.wirebarley.global.exception.exception;

import com.wirebarley.global.model.response.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public ErrorApiResponse<?> handleBusinessException(BusinessException e) {

        return ErrorApiResponse.of(HttpStatus.valueOf(e.getHttpStatus()), e.getMessage(), e.getErrorCode(), null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public ErrorApiResponse<?> handleUnauthorizedException(UnauthorizedException e) {

        return ErrorApiResponse.of(HttpStatus.valueOf(e.getHttpStatus()), e.getMessage(), e.getErrorCode(), null);
    }
}
