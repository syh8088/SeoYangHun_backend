package com.wirebarley.domain.wallet.repository;

import com.wirebarley.domain.wallet.enums.WalletTransactionType;
import com.wirebarley.domain.wallet.model.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>, WalletTransactionRepositoryCustom {

    @Query("select abs(sum(w.amount)) from WalletTransaction AS w where w.wallet.walletNo = :walletNo and w.type = :type and w.createdDateTime between :startDatetime and :endDatetime")
    BigDecimal selectDailyWithdrawalBalanceByWalletNo(
            @Param("walletNo") long walletNo,
            @Param("type") WalletTransactionType type,
            @Param("startDatetime") LocalDateTime startDatetime,
            @Param("endDatetime") LocalDateTime endDatetime
    );
}