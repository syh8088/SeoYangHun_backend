package com.wirebarley.domain.bank.repository;

import com.wirebarley.domain.bank.model.response.BankOutPut;

import java.util.List;
import java.util.Optional;

public interface BankRepositoryCustom {


    List<BankOutPut> selectBankList();

    Optional<BankOutPut> selectBankByBankNo(long bankNo);
}
