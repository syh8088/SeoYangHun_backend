package com.wirebarley.domain.bank.repository;

import com.wirebarley.domain.bank.model.response.BankOutPut;

import java.util.List;

public interface BankRepositoryCustom {


    List<BankOutPut> selectBankList();

}
