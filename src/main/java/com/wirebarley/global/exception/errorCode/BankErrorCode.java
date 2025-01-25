package com.wirebarley.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public enum BankErrorCode implements ErrorCode {

    NOT_FOUND_BANK("BKE0001", HttpStatus.NOT_FOUND.value()),


            ;

    private final String code;
    private final int httpStatus;

    BankErrorCode(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getCodePath() {
        return "error.bank." + code;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
