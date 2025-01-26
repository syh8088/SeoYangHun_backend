package com.wirebarley.api.bankaccount.model.response;

import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BankAccountResponse {

    private long bankAccountNo;
    private long bankNo;
    private String bankName;
    private int bankAccountNumber;

    @Builder
    private BankAccountResponse(long bankAccountNo, long bankNo, String bankName, int bankAccountNumber) {
        this.bankAccountNo = bankAccountNo;
        this.bankNo = bankNo;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }


    public static List<BankAccountResponse> of(List<BankAccountWithBankOutPut> bankAccountWithBankOutPuts) {
        return bankAccountWithBankOutPuts.stream()
                .map(BankAccountResponse::of)
                .toList();
    }

    public static BankAccountResponse of(BankAccountWithBankOutPut bankAccountWithBankOutPut) {
        return BankAccountResponse.builder()
                .bankAccountNo(bankAccountWithBankOutPut.getBankAccountNo())
                .bankNo(bankAccountWithBankOutPut.getBankNo())
                .bankAccountNumber(bankAccountWithBankOutPut.getBankAccountNumber())
                .bankName(bankAccountWithBankOutPut.getBankName())
                .build();
    }
}
