package com.wirebarley.domain.bankaccount.repository;

import com.wirebarley.domain.bankaccount.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, BankAccountRepositoryCustom {

}