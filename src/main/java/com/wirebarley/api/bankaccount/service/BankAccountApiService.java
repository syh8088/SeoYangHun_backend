package com.wirebarley.api.bankaccount.service;

import com.wirebarley.api.bankaccount.model.request.InsertBankAccountRequest;
import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;
import com.wirebarley.domain.bankaccount.service.BankAccountAdapter;
import com.wirebarley.domain.bankaccount.service.BankAccountService;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountApiService {

    private final MemberService memberService;
    private final BankAccountService bankAccountService;

    private final List<BankAccountAdapter> bankAccountAdapters;

    /**
     * <h1>은행 계좌 등록 합니다.</h1>
     * <pre>
     * - 계정 조회 하기
     * - 특정 은행 서버로 은행 정보 존재 여부 및 은행 계좌 정보 가져오기
     * - 해당 계정 은행 계좌 등록
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveBankAccount(long memberNo, InsertBankAccountRequest insertBankAccountRequest) {

        Member member = memberService.selectMemberEntityThenThrowExceptionByMemberNo(memberNo);

        BankAccountAdapter handlerBankAccountService = BankAccountAdapter.getHandlerBankAccountServices(bankAccountAdapters);
        BankAccountOutPut bankAccountInfo = handlerBankAccountService.getBankAccountInfo(insertBankAccountRequest.getBankAccountNumber());

        bankAccountService.saveBankAccount(member, bankAccountInfo);
    }
}
