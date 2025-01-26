package com.wirebarley.domain.bankaccount.repository;

import com.wirebarley.domain.bankaccount.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, BankAccountRepositoryCustom {

    @Modifying
    @Query("UPDATE BankAccount AS b SET b.isDeleted = :isDeleted WHERE b.bankAccountNo = :bankAccountNo")
    void updateBankAccountIsDeletedByBankAccountNo(@Param("bankAccountNo") long bankAccountNo, @Param("isDeleted") boolean isDeleted);
}