package com.wirebarley.domain.bankaccount.service;

import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bank.repository.BankRepository;
import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;
import com.wirebarley.global.exception.errorCode.BankErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class WooriBankAccountAdapter implements BankAccountAdapter {

    private final BankRepository bankRepository;

    private static final String BANK_RETRY_CONFIG = "bankRetryConfig";

    @Retry(name = BANK_RETRY_CONFIG, fallbackMethod = "fallbackGetBankAccountInfo")
    @Override
    public BankAccountOutPut getBankAccountInfo(int bankAccountNumber) {

        Bank bank = bankRepository.selectBankByBankName("우리은행")
                .orElseThrow(() ->  new BusinessException(BankErrorCode.NOT_FOUND_BANK));

        return BankAccountOutPut.of(bank.getBankNo(), bank.getBankName(), bankAccountNumber);
    }

    /**
     * resilience4j maxAttempts 설정한 횟수 전부 실패시 fallback 이 실행
     */
    private boolean fallbackGetBankAccountInfo(int bankAccountNumber, Exception exception) {
        log.error("fallbackGetBankAccountInfo! your request is bankAccountNumber={}, errorMessage={}", bankAccountNumber, exception.getMessage());

        return false;
    }

    @Retry(name = BANK_RETRY_CONFIG, fallbackMethod = "fallbackCheckBank")
    @Override
    public boolean checkBank(int bankAccountNumber) {
        return true;
    }

    /**
     * resilience4j maxAttempts 설정한 횟수 전부 실패시 fallback 이 실행
     */
    private boolean fallbackCheckBank(int bankAccountNumber, Exception exception) {
        log.error("fallbackCheckBank! your request is bankAccountNumber={}, errorMessage={}", bankAccountNumber, exception.getMessage());

        return false;
    }

    @Retry(name = BANK_RETRY_CONFIG, fallbackMethod = "fallbackDeposit")
    @Override
    public boolean deposit(BigDecimal depositAmount, int bankAccountNumber) {

//        if (true) {
//            throw new BankException();
//        }

        return true;
    }

    /**
     * resilience4j maxAttempts 설정한 횟수 전부 실패시 fallback 이 실행
     */
    private boolean fallbackDeposit(BigDecimal depositAmount, int bankAccountNumber, Exception exception) {
        log.error("fallbackDeposit! your request is depositAmount={}, bankAccountNumber={}, errorMessage={}", depositAmount, bankAccountNumber, exception.getMessage());

        return false;
    }

    @Retry(name = BANK_RETRY_CONFIG, fallbackMethod = "fallbackWithdraw")
    @Override
    public boolean withdraw(BigDecimal withdrawAmount, int bankAccountNumber) {
        return true;
    }

    /**
     * resilience4j maxAttempts 설정한 횟수 전부 실패시 fallback 이 실행
     */
    private boolean fallbackWithdraw(BigDecimal withdrawAmount, int bankAccountNumber, Exception exception) {
        log.error("fallbackWithdraw! your request is withdrawAmount={}, bankAccountNumber={}, errorMessage={}", withdrawAmount, bankAccountNumber, exception.getMessage());

        return false;
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
