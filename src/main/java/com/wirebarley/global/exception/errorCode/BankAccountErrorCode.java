package com.wirebarley.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public enum BankAccountErrorCode implements ErrorCode {

    NOT_FOUND_BANK_ACCOUNT("BAE0001", HttpStatus.NOT_FOUND.value()),
    NOT_EXIST_BANK_NAME("BAE0002", HttpStatus.BAD_REQUEST.value()),
    NOT_EXIST_BANK_ACCOUNT_NUMBER("BAE0003", HttpStatus.BAD_REQUEST.value()),
    ALREADY_CREATED_BANK_ACCOUNT("BAE0004", HttpStatus.BAD_REQUEST.value()),
    NOT_EXIST_BANK_ACCOUNT("BAE0005", HttpStatus.NOT_FOUND.value()),

            ;

    private final String code;
    private final int httpStatus;

    BankAccountErrorCode(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getCodePath() {
        return "error.bankaccount." + code;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
