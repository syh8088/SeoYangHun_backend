package com.wirebarley.domain.bankaccount.repository;

import com.wirebarley.domain.bankaccount.model.entity.BankAccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountHistoryRepository extends JpaRepository<BankAccountHistory, Long> {

}