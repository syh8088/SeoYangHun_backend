package com.wirebarley.global.model.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorApiResponse<T> {

    private final String code;
    private final int status;
    private final String message;
    private final T data;

    private ErrorApiResponse(int status, String message, String errorCode, T data) {
        this.code = errorCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ErrorApiResponse<T> of(int httpStatus, String message, String errorCode, T data) {
        return new ErrorApiResponse<>(httpStatus, message, errorCode, data);
    }
}