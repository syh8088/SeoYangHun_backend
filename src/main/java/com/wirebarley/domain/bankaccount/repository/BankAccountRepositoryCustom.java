package com.wirebarley.domain.bankaccount.repository;


import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepositoryCustom {

    Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNoAndBankAccountNo(long memberNo, long bankAccountNo);

    boolean existsBankAccountByMemberNoAndBankNoAndBankAccountNumberAndIsDeleted(long memberNo, long bankNo, int bankAccountNumber, boolean isDeleted);

    boolean existsBankAccountByMemberNoAndBankAccountNo(long memberNo, long bankAccountNo);

    Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNoAndBankNoAndBankAccountNumber(long memberNo, long bankAccountNo, int bankAccountNumber);

    List<BankAccountWithBankOutPut> selectBankAccountList(long memberNo);
}
