package com.wirebarley.domain.wallet.model.entity;

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
@Table(name = "wallets")
public class Wallet extends CommonEntity {

    @Id
    @Column(name = "wallet_no")
    private Long walletNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private BigDecimal balance;

    @Version
    private int version;

    @Builder
    private Wallet(Long walletNo, Member member, BigDecimal balance, int version) {
        this.walletNo = walletNo;
        this.member = member;
        this.balance = balance;
        this.version = version;
    }

    public static Wallet of(long walletNo, Member member) {
        return Wallet.builder()
                .walletNo(walletNo)
                .member(member)
                .balance(BigDecimal.ZERO)
                .build();
    }

    public static Wallet of(long walletNo) {
        return Wallet.builder()
                .walletNo(walletNo)
                .build();
    }

}