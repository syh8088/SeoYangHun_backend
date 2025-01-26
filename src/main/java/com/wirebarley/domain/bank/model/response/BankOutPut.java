package com.wirebarley.domain.bank.model.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankOutPut {

    private long bankNo;
    private String bankName;

    @QueryProjection
    public BankOutPut(long bankNo, String bankName) {
        this.bankNo = bankNo;
        this.bankName = bankName;
    }

}
