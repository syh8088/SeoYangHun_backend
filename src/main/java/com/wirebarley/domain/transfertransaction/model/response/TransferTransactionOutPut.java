package com.wirebarley.domain.transfertransaction.model.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TransferTransactionOutPut {

    private long transferTransactionNo;

    private long fromMemberNo;
    private long fromBankAccountNo;
    private long fromBankNo;
    private String fromBankName;
    private int fromBankAccountNumber;

    private long toBankNo;
    private String toBankName;
    private int toBankAccountNumber;
    private LocalDateTime createdDateTime;
    private BigDecimal transferAmount;
    private BigDecimal fee;
    private BigDecimal feeCalculatedAmount;

    @QueryProjection
    public TransferTransactionOutPut(long transferTransactionNo, long fromMemberNo, long fromBankAccountNo, long fromBankNo, String fromBankName, int fromBankAccountNumber, long toBankNo, String toBankName, int toBankAccountNumber, LocalDateTime createdDateTime, BigDecimal transferAmount, BigDecimal fee, BigDecimal feeCalculatedAmount) {
        this.transferTransactionNo = transferTransactionNo;
        this.fromMemberNo = fromMemberNo;
        this.fromBankAccountNo = fromBankAccountNo;
        this.fromBankNo = fromBankNo;
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankNo = toBankNo;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.createdDateTime = createdDateTime;
        this.transferAmount = transferAmount;
        this.fee = fee;
        this.feeCalculatedAmount = feeCalculatedAmount;
    }

    public static TransferTransactionOutPut of(long transferTransactionNo, long fromMemberNo, long fromBankAccountNo, long fromBankNo, String fromBankName, int fromBankAccountNumber, long toBankNo, String toBankName, int toBankAccountNumber, LocalDateTime createdDateTime, BigDecimal transferAmount, BigDecimal fee, BigDecimal feeCalculatedAmount) {
        return new TransferTransactionOutPut(transferTransactionNo, fromMemberNo, fromBankAccountNo, fromBankNo, fromBankName, fromBankAccountNumber, toBankNo, toBankName, toBankAccountNumber, createdDateTime, transferAmount, fee, feeCalculatedAmount);
    }
}
