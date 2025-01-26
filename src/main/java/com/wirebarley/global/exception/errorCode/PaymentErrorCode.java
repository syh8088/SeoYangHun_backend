package com.wirebarley.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public enum PaymentErrorCode implements ErrorCode {

    NOT_EXIST_PRODUCT("PEC0001", HttpStatus.BAD_REQUEST.value()),
    INVALID_PAYMENT_AMOUNT("PEC0002", HttpStatus.BAD_REQUEST.value()),
    NOT_EXIST_ORDER_ID("PEC0003", HttpStatus.BAD_REQUEST.value()),
    NOT_EXIST_AMOUNT("PEC0004", HttpStatus.BAD_REQUEST.value()),
    NOT_EXIST_PAYMENT_TYPE("PEC0005", HttpStatus.BAD_REQUEST.value()),

            ;

    private final String code;
    private final int httpStatus;

    PaymentErrorCode(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getCodePath() {
        return "error.payment." + code;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
