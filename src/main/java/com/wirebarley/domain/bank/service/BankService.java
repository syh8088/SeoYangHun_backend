package com.wirebarley.domain.bank.service;

import com.wirebarley.domain.bank.model.response.BankOutPut;
import com.wirebarley.domain.bank.repository.BankRepository;
import com.wirebarley.global.exception.errorCode.BankErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    @Transactional(readOnly = true)
    public List<BankOutPut> selectBankList() {
        return bankRepository.selectBankList();
    }

    @Transactional(readOnly = true)
    public BankOutPut selectBankByBankNo(long bankNo) {
        return bankRepository.selectBankByBankNo(bankNo)
                .orElseThrow(() ->  new BusinessException(BankErrorCode.NOT_FOUND_BANK));
    }
}
