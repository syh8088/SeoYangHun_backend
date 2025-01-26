package com.wirebarley.api.bank.service;

import com.wirebarley.domain.bank.model.response.BankOutPut;
import com.wirebarley.domain.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankApiService {

    private final BankService bankService;

    public List<BankOutPut> selectBankList() {
        return bankService.selectBankList();
    }
}
