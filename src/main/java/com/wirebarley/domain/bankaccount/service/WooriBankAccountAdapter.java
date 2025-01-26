package com.wirebarley.domain.bankaccount.service;

import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bank.repository.BankRepository;
import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;
import com.wirebarley.global.exception.errorCode.BankErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class WooriBankAccountAdapter implements BankAccountAdapter {

    private final BankRepository bankRepository;

    @Override
    public BankAccountOutPut getBankAccountInfo(int bankAccountNumber) {

        Bank bank = bankRepository.selectBankByBankName("우리은행")
                .orElseThrow(() ->  new BusinessException(BankErrorCode.NOT_FOUND_BANK));

        return BankAccountOutPut.of(bank.getBankNo(), bank.getBankName(), bankAccountNumber);
    }

    @Override
    public boolean checkBank(int bankAccountNumber) {
        return true;
    }

    @Override
    public boolean deposit(BigDecimal depositAmount, int bankAccountNumber) {
        return true;
    }

    @Override
    public boolean withdraw(BigDecimal withdrawAmount, int bankAccountNumber) {
        return true;
    }

    @Override
    public boolean transferTransaction(String fromBankName, int fromBankAccountNumber, String toBankName, int toBankAccountNumber, BigDecimal transferAmount) {
        return true;
    }

    @Override
    public boolean supports() {
        return true;
    }
}
