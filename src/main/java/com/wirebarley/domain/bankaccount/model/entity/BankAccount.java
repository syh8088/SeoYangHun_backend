package com.wirebarley.domain.bankaccount.model.entity;

import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bank_accounts")
public class BankAccount extends CommonEntity {

    @Id
    @Column(name = "bank_account_no")
    private Long bankAccountNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Bank bank;

    @Column(name = "bank_account_number")
    private int bankAccountNumber;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Builder
    private BankAccount(Long bankAccountNo, Member member, Bank bank, int bankAccountNumber, boolean isDeleted) {
        this.bankAccountNo = bankAccountNo;
        this.member = member;
        this.bank = bank;
        this.bankAccountNumber = bankAccountNumber;
        this.isDeleted = isDeleted;
    }

    public static BankAccount of(long bankAccountNo, Member member, long bankNo, int bankAccountNumber) {
        return BankAccount.builder()
                .bankAccountNo(bankAccountNo)
                .member(member)
                .bank(Bank.of(bankNo))
                .bankAccountNumber(bankAccountNumber)
                .isDeleted(false)
                .build();
    }

    public static BankAccount of(long bankAccountNo) {
        return BankAccount.builder()
                .bankAccountNo(bankAccountNo)
                .build();
    }
}