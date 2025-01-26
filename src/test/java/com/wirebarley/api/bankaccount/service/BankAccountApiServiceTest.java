package com.wirebarley.api.bankaccount.service;

import com.wirebarley.IntegrationTestSupport;
import com.wirebarley.api.bankaccount.model.request.InsertBankAccountRequest;
import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bank.repository.BankRepository;
import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;
import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;
import com.wirebarley.domain.bankaccount.service.BankAccountService;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.model.response.MemberWithWalletOutPut;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.domain.wallet.model.entity.Wallet;
import com.wirebarley.domain.wallet.repository.WalletRepository;
import com.wirebarley.global.exception.exception.BusinessException;
import com.wirebarley.global.util.Snowflake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BankAccountApiServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankAccountApiService bankAccountApiService;

    @Autowired
    private BankAccountService bankAccountService;

    private final Snowflake snowflake = new Snowflake();

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        walletRepository.deleteAllInBatch();
        bankRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName(" 계좌 등록 하기 TEST 합니다.")
    void saveBankAccount() {
        // given
        MemberWithWalletOutPut memberWithWalletOutPut = this.createMember();
        long memberNo = memberWithWalletOutPut.getMemberNo();
        long walletNo = memberWithWalletOutPut.getWalletNo();

        String bankName = "우리은행";
        int bankAccountNumber = 1234;
        InsertBankAccountRequest insertBankAccountRequest = InsertBankAccountRequest.of(bankName, bankAccountNumber);

        Bank bank = this.createBank(bankName);

        // stubbing
        this.createStubbingBankAccount(bank.getBankNo(), bank.getBankName(), bankAccountNumber);

        // when
        long bankAccountNo = bankAccountApiService.saveBankAccount(memberNo, bank.getBankNo(), insertBankAccountRequest);

        // then
        BankAccountWithBankOutPut bankAccountWithBankOutPut
                = bankAccountService.selectBankAccountThenThrowExceptionByMemberNoAndBankAccountNo(memberNo, bankAccountNo);
        assertThat(bankAccountWithBankOutPut).isNotNull();
        assertThat(bankAccountWithBankOutPut.getBankName()).isEqualTo(bank.getBankName());
        assertThat(bankAccountWithBankOutPut.getBankAccountNumber()).isEqualTo(bankAccountNumber);
        assertThat(bankAccountWithBankOutPut.getBankAccountNo()).isEqualTo(bankAccountNo);
    }

    @Test
    @DisplayName(" 계좌 삭제 하기 TEST 합니다.")
    void deleteBankAccount() {
        // given
        MemberWithWalletOutPut memberWithWalletOutPut = this.createMember();
        long memberNo = memberWithWalletOutPut.getMemberNo();
        long walletNo = memberWithWalletOutPut.getWalletNo();

        String bankName = "우리은행";
        int bankAccountNumber = 1234;
        InsertBankAccountRequest insertBankAccountRequest = InsertBankAccountRequest.of(bankName, bankAccountNumber);

        Bank bank = this.createBank(bankName);

        // stubbing
        this.createStubbingBankAccount(bank.getBankNo(), bank.getBankName(), bankAccountNumber);

        long bankAccountNo = bankAccountApiService.saveBankAccount(memberNo, bank.getBankNo(), insertBankAccountRequest);

        // when
        bankAccountApiService.deleteBankAccount(memberNo, bankAccountNo);

        // then
        assertThatThrownBy(() -> bankAccountService.selectBankAccountThenThrowExceptionByMemberNoAndBankAccountNo(memberNo, bankAccountNo))
                .isInstanceOf(BusinessException.class)
                .hasMessage("계좌가 존재하지 않습니다.");
    }

    private void createStubbingBankAccount(long bankNo, String bankName, int bankAccountNumber) {

        when(wooriBankAccountAdapter.supports()).thenReturn(true);
        BankAccountOutPut bankAccountOutPut = BankAccountOutPut.of(bankNo, bankName, bankAccountNumber);
        when(wooriBankAccountAdapter.getBankAccountInfo(
                any(Integer.class)
        )).thenReturn(bankAccountOutPut);
    }

    private MemberWithWalletOutPut createMember() {
        Member member = Member.of(snowflake.nextId(), "test", "홍길동", "1234");
        memberRepository.save(member);

        Wallet wallet = Wallet.of(snowflake.nextId(), member);
        walletRepository.save(wallet);

        return MemberWithWalletOutPut.of(member.getMemberNo(), wallet.getWalletNo());
    }

    private Bank createBank(String bankName) {
        Bank bank = Bank.of(snowflake.nextId(), bankName);
        return bankRepository.save(bank);
    }

}