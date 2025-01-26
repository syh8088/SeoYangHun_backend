package com.wirebarley.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public enum TransferTransactionErrorCode implements ErrorCode {

    DAILY_TRANSFER_LIMIT("TTE0001", HttpStatus.BAD_REQUEST.value()),

    ;

    private final String code;
    private final int httpStatus;

    TransferTransactionErrorCode(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getCodePath() {
        return "error.transfertransaction." + code;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
