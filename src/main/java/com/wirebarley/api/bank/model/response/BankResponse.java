package com.wirebarley.api.bank.model.response;

import com.wirebarley.domain.bank.model.response.BankOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BankResponse {

    private long bankNo;
    private String bankName;

    @Builder
    private BankResponse(long bankNo, String bankName) {
        this.bankNo = bankNo;
        this.bankName = bankName;
    }

    public static BankResponse of(BankOutPut bankOutPut) {
        return BankResponse.builder()
                .bankNo(bankOutPut.getBankNo())
                .bankName(bankOutPut.getBankName())
                .build();
    }

    public static List<BankResponse> of(List<BankOutPut> bankOutPutList) {
        return bankOutPutList.stream()
                .map(BankResponse::of)
                .toList();
    }

}
