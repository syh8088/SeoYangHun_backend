package com.wirebarley.domain.wallet.model.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wirebarley.domain.wallet.enums.WalletTransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WalletTransactionOutPut {

    private long walletNo;
    private long walletTransactionNo;
    private Long bankNo;
    private String bankName;
    private int bankAccountNumber;
    private BigDecimal amount;
    private WalletTransactionType type;
    private LocalDateTime createdDateTime;

    @QueryProjection
    public WalletTransactionOutPut(long walletNo, long walletTransactionNo, Long bankNo, String bankName, int bankAccountNumber, BigDecimal amount, WalletTransactionType type, LocalDateTime createdDateTime) {
        this.walletNo = walletNo;
        this.walletTransactionNo = walletTransactionNo;
        this.bankNo = bankNo;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.amount = amount;
        this.type = type;
        this.createdDateTime = createdDateTime;
    }
}
