package com.wirebarley.domain.bank.repository;

import com.wirebarley.domain.bank.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long>, BankRepositoryCustom {

    @Query("select b from Bank AS b where b.bankName = :bankName")
    Optional<Bank> selectBankByBankName(@Param("bankName") String bankName);
}