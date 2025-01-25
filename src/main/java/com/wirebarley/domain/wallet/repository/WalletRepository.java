package com.wirebarley.domain.wallet.repository;

import com.wirebarley.domain.wallet.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletRepositoryCustom {

    @Modifying
    @Query("UPDATE Wallet AS w SET w.balance = w.balance + :depositAmount, w.version = w.version + 1 WHERE w.walletNo = :walletNo")
    void updateDepositBalanceByWalletNo(@Param("walletNo") long walletNo, @Param("depositAmount") BigDecimal depositAmount);

    @Modifying
    @Query("UPDATE Wallet AS w SET w.balance = w.balance + :withdrawAmount, w.version = w.version + 1 WHERE w.walletNo = :walletNo")
    void updateWithdrawBalanceByWalletNo(@Param("walletNo") long walletNo, @Param("withdrawAmount") BigDecimal withdrawAmount);

    @Modifying
    @Query("UPDATE Wallet AS w SET w.balance = w.balance - :depositAmount, w.version = w.version + 1 WHERE w.walletNo = :walletNo")
    void updateBalanceOrderByWalletNo(@Param("walletNo") long walletNo, @Param("depositAmount") BigDecimal depositAmount);

    @Modifying
    @Query("UPDATE Wallet AS w SET w.balance = w.balance + :refundAmount, w.version = w.version + 1 WHERE w.walletNo = :walletNo")
    void updateRefundBalance(@Param("walletNo") long walletNo, @Param("refundAmount") BigDecimal refundAmount);
}