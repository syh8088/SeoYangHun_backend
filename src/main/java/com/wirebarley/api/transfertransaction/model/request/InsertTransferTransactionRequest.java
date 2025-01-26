package com.wirebarley.api.transfertransaction.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class InsertTransferTransactionRequest {

    private long fromBankAccountNo;
    private long toBankNo;
    private int toBankAccountNumber;
    private BigDecimal transferAmount;

    @Builder
    private InsertTransferTransactionRequest(long fromBankAccountNo, long toBankNo, int toBankAccountNumber, BigDecimal transferAmount) {
        this.fromBankAccountNo = fromBankAccountNo;
        this.toBankNo = toBankNo;
        this.toBankAccountNumber = toBankAccountNumber;
        this.transferAmount = transferAmount;
    }

    public static InsertTransferTransactionRequest of(long fromBankAccountNo, long toBankNo, int toBankAccountNumber, BigDecimal transferAmount) {
        return InsertTransferTransactionRequest.builder()
                .fromBankAccountNo(fromBankAccountNo)
                .toBankNo(toBankNo)
                .toBankAccountNumber(toBankAccountNumber)
                .transferAmount(transferAmount)
                .build();
    }
}
