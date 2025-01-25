package com.wirebarley.domain.wallet.model.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WalletOutPut {

    private long walletNo;
    private BigDecimal balance;

    @QueryProjection
    public WalletOutPut(long walletNo, BigDecimal balance) {
        this.walletNo = walletNo;
        this.balance = balance;
    }
}
