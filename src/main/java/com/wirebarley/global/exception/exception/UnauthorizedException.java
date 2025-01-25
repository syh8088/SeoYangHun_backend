package com.wirebarley.global.exception.exception;

import com.wirebarley.global.exception.errorCode.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
    private final int httpStatus;

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
        this.message = CustomMessageHandler.getMessage(errorCode.getCodePath());
        this.httpStatus = errorCode.getHttpStatus();
    }

    public UnauthorizedException(ErrorCode errorCode, Object[] errorMessages) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
        this.message = CustomMessageHandler.getMessage(errorCode.getCode(), errorMessages);
        this.httpStatus = errorCode.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }
}
