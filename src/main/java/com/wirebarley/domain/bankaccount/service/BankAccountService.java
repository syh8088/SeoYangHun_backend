package com.wirebarley.domain.bankaccount.service;

import com.wirebarley.domain.bankaccount.model.entity.BankAccount;
import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;
import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;
import com.wirebarley.domain.bankaccount.repository.BankAccountRepository;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.global.exception.errorCode.BankAccountErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import com.wirebarley.global.util.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final Snowflake snowflake = new Snowflake();

    /**
     * <h1>해당 계정으로 은행 계좌 등록</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public void saveBankAccount(Member member, BankAccountOutPut bankAccountOutPut) {

        BankAccount bankAccount = BankAccount.of(snowflake.nextId(), member, bankAccountOutPut.getBankNo(), bankAccountOutPut.getBankAccountNumber());
        bankAccountRepository.save(bankAccount);
    }

    /**
     * <h1>해당 계정으로 은행 계좌 조회 합니다.</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(readOnly = true)
    public BankAccountWithBankOutPut selectBankAccountThenThrowExceptionByMemberNo(long memberNo) {
        return bankAccountRepository.selectBankAccountByMemberNo(memberNo)
                .orElseThrow(() ->  new BusinessException(BankAccountErrorCode.NOT_FOUND_BANK_ACCOUNT));
    }
}
