package com.wirebarley.api.bank.model.response;

import com.wirebarley.domain.bank.model.response.BankOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BankResponses {

    private List<BankResponse> bankList;

    @Builder
    private BankResponses(List<BankResponse> bankList) {
        this.bankList = bankList;
    }

    public static BankResponses of(List<BankOutPut> bankOutPutList) {
        List<BankResponse> bankResponses = BankResponse.of(bankOutPutList);
        return BankResponses.builder()
                .bankList(bankResponses)
                .build();
    }
}
