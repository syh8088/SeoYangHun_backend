package com.wirebarley.domain.bankaccount.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankAccountOutPut {

    private long bankNo;
    private String bankName;
    private int bankAccountNumber;

    @Builder
    private BankAccountOutPut(long bankNo, String bankName, int bankAccountNumber) {
        this.bankNo = bankNo;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }

    public static BankAccountOutPut of(long bankNo, String bankName, int bankAccountNumber) {
        return BankAccountOutPut.builder()
                .bankNo(bankNo)
                .bankName(bankName)
                .bankAccountNumber(bankAccountNumber)
                .build();
    }
}
