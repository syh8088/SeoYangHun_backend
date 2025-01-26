package com.wirebarley.domain.transfertransaction.model.entity;

import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bankaccount.model.entity.BankAccount;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "transfer_transactions")
public class TransferTransaction extends CommonEntity {

    @Id
    @Column(name = "transfer_transaction_no")
    private Long transferTransactionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_bank_account_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private BankAccount fromBankAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_bank_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Bank toBank;

    @Column(name = "to_bank_account_number")
    private int toBankAccountNumber;

    @Column(name = "transfer_amount")
    private BigDecimal transferAmount;

    @Builder
    private TransferTransaction(Long transferTransactionNo, Member fromMember, BankAccount fromBankAccount, Bank toBank, int toBankAccountNumber, BigDecimal transferAmount) {
        this.transferTransactionNo = transferTransactionNo;
        this.fromMember = fromMember;
        this.fromBankAccount = fromBankAccount;
        this.toBank = toBank;
        this.toBankAccountNumber = toBankAccountNumber;
        this.transferAmount = transferAmount;
    }

    public static TransferTransaction of(long transferTransactionNo, long fromMemberNo, long fromBankAccountNo, long toBankNo, int toBankAccountNumber, BigDecimal transferAmount) {

        Member fromMember = Member.of(fromMemberNo);
        BankAccount fromBankAccount = BankAccount.of(fromBankAccountNo);
        Bank toBank = Bank.of(toBankNo);

        return TransferTransaction.builder()
                .transferTransactionNo(transferTransactionNo)
                .fromMember(fromMember)
                .fromBankAccount(fromBankAccount)
                .toBank(toBank)
                .toBankAccountNumber(toBankAccountNumber)
                .transferAmount(transferAmount)
                .build();
    }
}