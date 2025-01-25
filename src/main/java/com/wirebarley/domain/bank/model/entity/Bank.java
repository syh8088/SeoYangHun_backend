package com.wirebarley.domain.bank.model.entity;

import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "banks")
public class Bank extends CommonEntity {

    @Id
    @Column(name = "bank_no")
    private Long bankNo;

    @Column(name = "bank_name")
    private String bankName;

    @Builder
    private Bank(Long bankNo, String bankName) {
        this.bankNo = bankNo;
        this.bankName = bankName;
    }

    public static Bank of(long bankNo) {
        return Bank.builder()
                .bankNo(bankNo)
                .build();
    }

    public static Bank of(long bankNo, String bankName) {
        return Bank.builder()
                .bankNo(bankNo)
                .bankName(bankName)
                .build();
    }
}