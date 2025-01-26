package com.wirebarley.domain.transfertransaction.repository;

import com.wirebarley.domain.transfertransaction.model.entity.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long>, TransferTransactionRepositoryCustom {

    @Query("select sum(t.transferAmount) from TransferTransaction AS t where t.fromMember.memberNo = :fromMemberNo and t.createdDateTime between :startDatetime and :endDatetime")
    BigDecimal selectDailyTransferByMemberNo(
            @Param("fromMemberNo") long fromMemberNo,
            @Param("startDatetime") LocalDateTime startDatetime,
            @Param("endDatetime") LocalDateTime endDatetime
    );
}