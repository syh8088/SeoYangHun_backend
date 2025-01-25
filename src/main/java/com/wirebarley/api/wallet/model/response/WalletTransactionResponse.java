package com.wirebarley.api.wallet.model.response;

import com.wirebarley.domain.wallet.enums.WalletTransactionType;
import com.wirebarley.domain.wallet.model.response.WalletTransactionOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class WalletTransactionResponse {

    private long walletNo;
    private long walletTransactionNo;
    private Long bankNo;
    private String bankName;
    private int bankAccountNumber;
    private BigDecimal amount;
    private WalletTransactionType type;
    private LocalDateTime createdDateTime;

    @Builder
    private WalletTransactionResponse(long walletNo, long walletTransactionNo, Long bankNo, String bankName, int bankAccountNumber, BigDecimal amount, WalletTransactionType type, LocalDateTime createdDateTime) {
        this.walletNo = walletNo;
        this.walletTransactionNo = walletTransactionNo;
        this.bankNo = bankNo;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.amount = amount;
        this.type = type;
        this.createdDateTime = createdDateTime;
    }

    public static WalletTransactionResponse of(WalletTransactionOutPut walletTransaction) {
        return WalletTransactionResponse.builder()
                .walletNo(walletTransaction.getWalletNo())
                .walletTransactionNo(walletTransaction.getWalletTransactionNo())
                .bankNo(walletTransaction.getBankNo())
                .bankName(walletTransaction.getBankName())
                .bankAccountNumber(walletTransaction.getBankAccountNumber())
                .amount(walletTransaction.getAmount())
                .type(walletTransaction.getType())
                .createdDateTime(walletTransaction.getCreatedDateTime())
                .build();
    }

    public static List<WalletTransactionResponse> of(List<WalletTransactionOutPut> walletTransactionList) {
        return walletTransactionList.stream()
                .map(WalletTransactionResponse::of)
                .toList();
    }
}
