package com.wirebarley.domain.wallet.model.entity;

import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.wallet.enums.WalletTransactionType;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wallet_transactions")
public class WalletTransaction extends CommonEntity {

    @Id
    @Column(name = "wallet_transaction_no")
    private Long walletTransactionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Bank bank;

    @Column(name = "bank_account_number")
    private int bankAccountNumber;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    private WalletTransactionType type;

    @Builder
    private WalletTransaction(Long walletTransactionNo, Wallet wallet, Bank bank, int bankAccountNumber, BigDecimal amount, WalletTransactionType type) {
        this.walletTransactionNo = walletTransactionNo;
        this.wallet = wallet;
        this.bank = bank;
        this.bankAccountNumber = bankAccountNumber;
        this.amount = amount;
        this.type = type;
    }

    public static WalletTransaction of(long walletTransactionNo, Wallet wallet, Bank bank, int bankAccountNumber, BigDecimal depositAmount, WalletTransactionType walletTransactionType) {
        return WalletTransaction.builder()
                .walletTransactionNo(walletTransactionNo)
                .wallet(wallet)
                .bank(bank)
                .bankAccountNumber(bankAccountNumber)
                .amount(depositAmount)
                .type(walletTransactionType)
                .build();
    }
}