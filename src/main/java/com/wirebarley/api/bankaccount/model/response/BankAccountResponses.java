package com.wirebarley.api.bankaccount.model.response;

import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BankAccountResponses {

    private List<BankAccountResponse> bankAccountList;

    @Builder
    private BankAccountResponses(List<BankAccountResponse> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public static BankAccountResponses of(List<BankAccountWithBankOutPut> bankOutPutList) {

        List<BankAccountResponse> bankAccountResponses = BankAccountResponse.of(bankOutPutList);
        return new BankAccountResponses(bankAccountResponses);
    }
}
