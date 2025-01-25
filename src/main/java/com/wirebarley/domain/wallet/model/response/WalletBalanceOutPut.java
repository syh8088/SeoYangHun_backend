package com.wirebarley.domain.wallet.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WalletBalanceOutPut {

    private BigDecimal balance;

    @Builder
    private WalletBalanceOutPut(BigDecimal balance) {
        this.balance = balance;
    }

    public static WalletBalanceOutPut of(BigDecimal balance) {
        return new WalletBalanceOutPut(balance);
    }
}
