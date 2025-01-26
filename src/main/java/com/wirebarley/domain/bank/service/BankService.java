package com.wirebarley.domain.bank.service;

import com.wirebarley.domain.bank.model.response.BankOutPut;
import com.wirebarley.domain.bank.repository.BankRepository;
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
}
