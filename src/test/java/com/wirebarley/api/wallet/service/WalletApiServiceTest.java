package com.wirebarley.api.wallet.service;

import com.wirebarley.IntegrationTestSupport;
import com.wirebarley.api.bankaccount.model.request.InsertBankAccountRequest;
import com.wirebarley.api.bankaccount.service.BankAccountApiService;
import com.wirebarley.api.wallet.model.request.WalletDepositRequest;
import com.wirebarley.api.wallet.model.request.WalletWithdrawRequest;
import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bank.repository.BankRepository;
import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;
import com.wirebarley.domain.bankaccount.service.BankAccountService;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.model.response.MemberWithWalletOutPut;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.domain.wallet.model.entity.Wallet;
import com.wirebarley.domain.wallet.model.response.WalletBalanceOutPut;
import com.wirebarley.domain.wallet.model.response.WalletTransactionOutPut;
import com.wirebarley.domain.wallet.repository.WalletRepository;
import com.wirebarley.global.exception.exception.BusinessException;
import com.wirebarley.global.util.Snowflake;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class WalletApiServiceTest extends IntegrationTestSupport {

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

    @Autowired
    private WalletApiService walletApiService;

    private final Snowflake snowflake = new Snowflake();

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        walletRepository.deleteAllInBatch();
        bankRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("입금 TEST 를 합니다.")
    void deposit() {

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

        // stubbing
        this.createStubbingBankAccountDeposit();

        // when
        WalletDepositRequest walletDepositRequest = WalletDepositRequest.of(BigDecimal.valueOf(10000));
        walletApiService.deposit(memberNo, bankAccountNo, walletDepositRequest);

        // then
        List<WalletTransactionOutPut> walletTransactionList = walletRepository.selectWalletTransactionList(walletNo);
        assertThat(walletTransactionList).hasSize(1)
                .extracting("walletNo", "bankNo", "bankName", "bankAccountNumber", "amount")
                .usingRecursiveFieldByFieldElementComparator(
                        RecursiveComparisonConfiguration.builder()
                                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class).build()
                )
                .containsExactlyInAnyOrder(
                        tuple(walletNo, bank.getBankNo(), bank.getBankName(), bankAccountNumber, walletDepositRequest.getDepositAmount())
                );
    }

    @Test
    @DisplayName("출금 TEST 를 합니다.")
    void withdraw() {

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

        // stubbing
        this.createStubbingBankAccountDeposit();
        this.createStubbingBankAccountWithdraw();

        // when
        WalletDepositRequest walletDepositRequest = WalletDepositRequest.of(BigDecimal.valueOf(10000));
        walletApiService.deposit(memberNo, bankAccountNo, walletDepositRequest);

        WalletWithdrawRequest walletWithdrawRequest = WalletWithdrawRequest.of(BigDecimal.valueOf(10000));
        walletApiService.withdraw(memberNo, bankAccountNo, walletWithdrawRequest);

        // then
        List<WalletTransactionOutPut> walletTransactionList = walletRepository.selectWalletTransactionList(walletNo);
        assertThat(walletTransactionList).hasSize(2)
                .extracting("walletNo", "bankNo", "bankName", "bankAccountNumber", "amount")
                .usingRecursiveFieldByFieldElementComparator(
                        RecursiveComparisonConfiguration.builder()
                                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class).build()
                )
                .containsExactlyInAnyOrder(
                        tuple(walletNo, bank.getBankNo(), bank.getBankName(), bankAccountNumber, walletDepositRequest.getDepositAmount()),
                        tuple(walletNo, bank.getBankNo(), bank.getBankName(), bankAccountNumber, this.createNegativeAmount(walletWithdrawRequest.getWithdrawAmount()))
                );

    }

    @Test
    @DisplayName("출금시 1일 출금 한도: 100 만 원 Exception 발생 테스트")
    void dailyWithdrawalLimitThenThrow() {

        // given
        MemberWithWalletOutPut memberWithWalletOutPut = this.createMember();
        long memberNo = memberWithWalletOutPut.getMemberNo();

        String bankName = "우리은행";
        int bankAccountNumber = 1234;
        InsertBankAccountRequest insertBankAccountRequest = InsertBankAccountRequest.of(bankName, bankAccountNumber);

        Bank bank = this.createBank(bankName);

        // stubbing
        this.createStubbingBankAccount(bank.getBankNo(), bank.getBankName(), bankAccountNumber);

        long bankAccountNo = bankAccountApiService.saveBankAccount(memberNo, bank.getBankNo(), insertBankAccountRequest);

        // stubbing
        this.createStubbingBankAccountDeposit();
        this.createStubbingBankAccountWithdraw();

        // when // then
        WalletDepositRequest walletDepositRequest = WalletDepositRequest.of(BigDecimal.valueOf(2_000_000));
        walletApiService.deposit(memberNo, bankAccountNo, walletDepositRequest);

        WalletWithdrawRequest walletWithdrawRequest = WalletWithdrawRequest.of(BigDecimal.valueOf(500_000));
        walletApiService.withdraw(memberNo, bankAccountNo, walletWithdrawRequest);
        walletApiService.withdraw(memberNo, bankAccountNo, walletWithdrawRequest);

        assertThatThrownBy(() -> walletApiService.withdraw(memberNo, bankAccountNo, walletWithdrawRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("1일 출금 한도는 1,000,000원 금액 까지만 가능 합니다.");
    }

    @Test
    @DisplayName("Wallet 잔액 조회 테스트")
    void walletBalanceTest() {

        // given
        MemberWithWalletOutPut memberWithWalletOutPut = this.createMember();
        long memberNo = memberWithWalletOutPut.getMemberNo();

        String bankName = "우리은행";
        int bankAccountNumber = 1234;
        InsertBankAccountRequest insertBankAccountRequest = InsertBankAccountRequest.of(bankName, bankAccountNumber);

        Bank bank = this.createBank(bankName);

        // stubbing
        this.createStubbingBankAccount(bank.getBankNo(), bank.getBankName(), bankAccountNumber);

        long bankAccountNo = bankAccountApiService.saveBankAccount(memberNo, bank.getBankNo(), insertBankAccountRequest);

        // stubbing
        this.createStubbingBankAccountDeposit();
        this.createStubbingBankAccountWithdraw();

        // when
        WalletDepositRequest walletDepositRequest = WalletDepositRequest.of(BigDecimal.valueOf(50_000_000));
        walletApiService.deposit(memberNo, bankAccountNo, walletDepositRequest);

        WalletWithdrawRequest walletWithdrawRequest = WalletWithdrawRequest.of(BigDecimal.valueOf(8_000_000));
        walletApiService.withdraw(memberNo, bankAccountNo, walletWithdrawRequest);

        BigDecimal depositAmount = walletDepositRequest.getDepositAmount();
        BigDecimal withdrawAmount = walletWithdrawRequest.getWithdrawAmount();

        BigDecimal resultAmount = depositAmount.subtract(withdrawAmount);

        // then
        WalletBalanceOutPut walletBalanceOutPut = walletApiService.selectBalance(memberNo);
        assertThat(walletBalanceOutPut.getBalance()).isNotNull();
        assertThat(walletBalanceOutPut.getBalance()).isEqualByComparingTo(resultAmount);
    }

    private void createStubbingBankAccount(long bankNo, String bankName, int bankAccountNumber) {

        when(wooriBankAccountAdapter.supports()).thenReturn(true);
        BankAccountOutPut bankAccountOutPut = BankAccountOutPut.of(bankNo, bankName, bankAccountNumber);
        when(wooriBankAccountAdapter.getBankAccountInfo(
                any(Integer.class)
        )).thenReturn(bankAccountOutPut);
    }

    private void createStubbingBankAccountDeposit() {

        when(wooriBankAccountAdapter.supports()).thenReturn(true);
        when(wooriBankAccountAdapter.checkBank(
                any(Integer.class)
        )).thenReturn(true);
        when(wooriBankAccountAdapter.deposit(
                any(BigDecimal.class),
                any(Integer.class)
        )).thenReturn(true);
    }

    private void createStubbingBankAccountWithdraw() {

        when(wooriBankAccountAdapter.withdraw(
                any(BigDecimal.class),
                any(Integer.class)
        )).thenReturn(true);
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

    private BigDecimal createNegativeAmount(BigDecimal withdrawAmount) {
        BigDecimal minusQty = new BigDecimal(-1);
        withdrawAmount = withdrawAmount.multiply(minusQty);
        return withdrawAmount;
    }
}