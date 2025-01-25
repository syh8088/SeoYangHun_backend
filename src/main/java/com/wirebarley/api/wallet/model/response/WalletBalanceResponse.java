package com.wirebarley.api.wallet.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WalletBalanceResponse {

    private BigDecimal balance;

    @Builder
    private WalletBalanceResponse(BigDecimal balance) {
        this.balance = balance;
    }

    public static WalletBalanceResponse of(BigDecimal balance) {
        return new WalletBalanceResponse(balance);
    }
}
