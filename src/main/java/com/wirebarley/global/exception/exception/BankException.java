package com.wirebarley.global.exception.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public BankException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}