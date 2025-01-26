package com.wirebarley.domain.transfertransaction.repository;

import com.wirebarley.domain.transfertransaction.model.entity.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long>, TransferTransactionRepositoryCustom {

    @Query("select sum(t.transferAmount) from TransferTransaction AS t where t.fromMember = :fromMember and t.createdDateTime between :startDatetime and :endDatetime")
    BigDecimal selectDailyTransferByMemberNo(
            @Param("fromMember") long fromMember,
            @Param("startDatetime") LocalDateTime startDatetime,
            @Param("endDatetime") LocalDateTime endDatetime
    );
}