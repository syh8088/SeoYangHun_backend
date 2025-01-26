package com.wirebarley.api.transfertransaction.model.response;

import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TransferTransactionResponse {

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

    @Builder
    private TransferTransactionResponse(long transferTransactionNo, long fromMemberNo, long fromBankAccountNo, long fromBankNo, String fromBankName, int fromBankAccountNumber, long toBankNo, String toBankName, int toBankAccountNumber, LocalDateTime createdDateTime, BigDecimal transferAmount) {
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
    }

    public static List<TransferTransactionResponse> of(List<TransferTransactionOutPut> transferTransactionOutPut) {
        return transferTransactionOutPut.stream()
                .map(TransferTransactionResponse::of)
                .toList();
    }

    public static TransferTransactionResponse of(TransferTransactionOutPut transferTransactionOutPut) {
        return TransferTransactionResponse.builder()
                .transferTransactionNo(transferTransactionOutPut.getTransferTransactionNo())
                .fromMemberNo(transferTransactionOutPut.getFromMemberNo())
                .fromBankAccountNo(transferTransactionOutPut.getFromBankAccountNo())
                .fromBankNo(transferTransactionOutPut.getFromBankNo())
                .fromBankName(transferTransactionOutPut.getFromBankName())
                .fromBankAccountNumber(transferTransactionOutPut.getFromBankAccountNumber())
                .toBankNo(transferTransactionOutPut.getToBankNo())
                .toBankName(transferTransactionOutPut.getToBankName())
                .toBankAccountNumber(transferTransactionOutPut.getToBankAccountNumber())
                .createdDateTime(transferTransactionOutPut.getCreatedDateTime())
                .transferAmount(transferTransactionOutPut.getTransferAmount())
                .build();
    }
}
