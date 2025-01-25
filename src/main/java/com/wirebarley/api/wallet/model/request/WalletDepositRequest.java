package com.wirebarley.api.wallet.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WalletDepositRequest {

    private BigDecimal depositAmount;

    @Builder
    private WalletDepositRequest(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public static WalletDepositRequest of(BigDecimal depositAmount) {
        return builder().depositAmount(depositAmount).build();
    }
}
