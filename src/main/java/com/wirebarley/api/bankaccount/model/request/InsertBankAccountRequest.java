package com.wirebarley.api.bankaccount.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InsertBankAccountRequest {

    private String bankName;
    private int bankAccountNumber;

    @Builder
    private InsertBankAccountRequest(String bankName, int bankAccountNumber) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }

    public static InsertBankAccountRequest of(String bankName, int bankAccountNumber) {
        return InsertBankAccountRequest.builder()
                .bankName(bankName)
                .bankAccountNumber(bankAccountNumber)
                .build();
    }
}
