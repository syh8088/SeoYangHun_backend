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

import java.util.List;
import java.util.Optional;

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
    public long saveBankAccount(Member member, BankAccountOutPut bankAccountOutPut) {

        BankAccount bankAccount = BankAccount.of(snowflake.nextId(), member, bankAccountOutPut.getBankNo(), bankAccountOutPut.getBankAccountNumber());
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        return savedBankAccount.getBankAccountNo();
    }

    /**
     * <h1>해당 계정으로 은행 계좌 조회 합니다.</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public BankAccountWithBankOutPut selectBankAccountThenThrowExceptionByMemberNoAndBankAccountNo(long memberNo, long bankAccountNo) {
        return this.selectBankAccountByMemberNoAndBankAccountNo(memberNo, bankAccountNo)
                .orElseThrow(() ->  new BusinessException(BankAccountErrorCode.NOT_FOUND_BANK_ACCOUNT));
    }

    /**
     * <h1>해당 계정으로 은행 계좌 조회 합니다.</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNoAndBankAccountNo(long memberNo, long bankAccountNo) {
        return bankAccountRepository.selectBankAccountByMemberNoAndBankAccountNo(memberNo, bankAccountNo);
    }

    /**
     * <h1>해당 계정으로 은행 계좌 조회 합니다.</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNoAndBankNoAndBankAccountNumber(long memberNo, long bankNo, int bankAccountNumber) {
        return bankAccountRepository.selectBankAccountByMemberNoAndBankNoAndBankAccountNumber(memberNo, bankNo, bankAccountNumber);
    }

    /**
     * <h1>등록된 은행 계좌 {@link BankAccount#isDeleted} update 합니다.</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public void updateBankAccountIsDeletedByBankAccountNo(long bankAccountNo, boolean isDeleted) {
        bankAccountRepository.updateBankAccountIsDeletedByBankAccountNo(bankAccountNo, isDeleted);
    }

    /**
     * <h1>등록된 은행 계좌 조회 합니다.</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(readOnly = true)
    public List<BankAccountWithBankOutPut> selectBankAccountList(long memberNo) {
        return bankAccountRepository.selectBankAccountList(memberNo);
    }
}
