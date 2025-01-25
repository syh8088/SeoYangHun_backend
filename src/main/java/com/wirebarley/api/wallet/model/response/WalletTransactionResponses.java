package com.wirebarley.api.wallet.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WalletTransactionResponses {

    List<WalletTransactionResponse> walletTransactionList;

    @Builder
    private WalletTransactionResponses(List<WalletTransactionResponse> walletTransactionList) {
        this.walletTransactionList = walletTransactionList;
    }

    public static WalletTransactionResponses of(List<WalletTransactionResponse> walletTransactionList) {
        return WalletTransactionResponses.builder()
                .walletTransactionList(walletTransactionList)
                .build();
    }
}
