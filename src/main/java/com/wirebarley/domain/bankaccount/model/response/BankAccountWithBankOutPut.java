package com.wirebarley.domain.bankaccount.model.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankAccountWithBankOutPut {

    private long bankAccountNo;
    private long bankNo;
    private String bankName;
    private int bankAccountNumber;

    @QueryProjection
    public BankAccountWithBankOutPut(long bankAccountNo, long bankNo, String bankName, int bankAccountNumber) {
        this.bankAccountNo = bankAccountNo;
        this.bankNo = bankNo;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }

}
