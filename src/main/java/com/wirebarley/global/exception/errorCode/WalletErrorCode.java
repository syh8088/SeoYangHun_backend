package com.wirebarley.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public enum WalletErrorCode implements ErrorCode {

    NOT_FOUND_WALLET("WAE0001", HttpStatus.NOT_FOUND.value()),
    BALANCE_LESS_THAN_ZERO("WAE0002", HttpStatus.BAD_REQUEST.value()),
    INSUFFICIENT_BALANCE("WAE0003", HttpStatus.BAD_REQUEST.value()),
    ONE_TIME_WITHDRAWAL_LIMIT("WAE0004", HttpStatus.BAD_REQUEST.value()),
    DAILY_WITHDRAWAL_LIMIT("WAE0005", HttpStatus.BAD_REQUEST.value()),

    ;

    private final String code;
    private final int httpStatus;

    WalletErrorCode(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getCodePath() {
        return "error.wallet." + code;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
