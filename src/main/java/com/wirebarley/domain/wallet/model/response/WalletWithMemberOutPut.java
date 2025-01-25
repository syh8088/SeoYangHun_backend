package com.wirebarley.domain.wallet.model.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WalletWithMemberOutPut {

    private long memberNo;
    private long walletNo;
    private BigDecimal balance;

    @QueryProjection
    public WalletWithMemberOutPut(long memberNo, long walletNo, BigDecimal balance) {
        this.memberNo = memberNo;
        this.walletNo = walletNo;
        this.balance = balance;
    }
}
