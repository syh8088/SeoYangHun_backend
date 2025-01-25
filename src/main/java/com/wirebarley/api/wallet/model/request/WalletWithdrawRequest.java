package com.wirebarley.api.wallet.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WalletWithdrawRequest {

    private BigDecimal withdrawAmount;

    @Builder
    private WalletWithdrawRequest(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public static WalletWithdrawRequest of(BigDecimal withdrawAmount) {
        return WalletWithdrawRequest.builder()
                .withdrawAmount(withdrawAmount)
                .build();
    }
}
