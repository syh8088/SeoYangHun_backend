package com.wirebarley.domain.bankaccount.repository;


import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;

import java.util.Optional;

public interface BankAccountRepositoryCustom {


    Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNo(long memberNo);

    boolean existsBankAccountByMemberNoAndBankNo(long memberNo, long bankNo);
}
