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
@Table(name = "bank_account_histories")
public class BankAccountHistory extends CommonEntity {

    @Id
    @Column(name = "bank_account_history_no")
    private Long bankAccountHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Bank bank;

    @Column(name = "bank_account_number")
    private int bankAccountNumber;

    @Builder
    private BankAccountHistory(Long bankAccountHistoryNo, Member member, Bank bank, int bankAccountNumber) {
        this.bankAccountHistoryNo = bankAccountHistoryNo;
        this.member = member;
        this.bank = bank;
        this.bankAccountNumber = bankAccountNumber;
    }

    public static BankAccountHistory of(long bankAccountHistoryNo, Member member, long bankNo, int bankAccountNumber) {
        return BankAccountHistory.builder()
                .bankAccountHistoryNo(bankAccountHistoryNo)
                .member(member)
                .bank(Bank.of(bankNo))
                .bankAccountNumber(bankAccountNumber)
                .build();
    }
}