package com.wirebarley.domain.wallet.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum WalletTransactionType {

    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW")

    ;

    private final String type;

    WalletTransactionType(String type) {
        this.type = type;
    }

    public String getWalletTransactionType() {
        return this.type;
    }

    public static WalletTransactionType getByWalletTransactionType(String type) {
        return Arrays.stream(WalletTransactionType.values())
                .filter(data -> data.getWalletTransactionType().equals(type))
                .findFirst()
                .orElse(null);
    }
}
