package com.wirebarley.api.transfertransaction.service;

import com.wirebarley.IntegrationTestSupport;
import com.wirebarley.api.bankaccount.model.request.InsertBankAccountRequest;
import com.wirebarley.api.bankaccount.service.BankAccountApiService;
import com.wirebarley.api.transfertransaction.model.request.SelectTransferTransactionListRequest;
import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bank.repository.BankRepository;
import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;
import com.wirebarley.domain.bankaccount.service.BankAccountService;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.model.response.MemberWithWalletOutPut;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import com.wirebarley.domain.transfertransaction.repository.TransferTransactionMapper;
import com.wirebarley.domain.transfertransaction.repository.TransferTransactionRepository;
import com.wirebarley.domain.wallet.model.entity.Wallet;
import com.wirebarley.domain.wallet.repository.WalletRepository;
import com.wirebarley.global.util.Snowflake;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransferTransactionApiServiceTest extends IntegrationTestSupport {

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
    private TransferTransactionRepository transferTransactionRepository;

    @Autowired
    private TransferTransactionApiService transferTransactionApiService;

    @Autowired
    private TransferTransactionMapper transferTransactionMapper;

    private final Snowflake snowflake = new Snowflake();

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        walletRepository.deleteAllInBatch();
        bankRepository.deleteAllInBatch();
        transferTransactionRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("계좌 이체 실행 및 계좌 이체 조회 테스트 합니다.")
    void saveTransferTransaction() {
        // given
        MemberWithWalletOutPut memberWithWalletOutPut = this.createMember();
        long memberNo = memberWithWalletOutPut.getMemberNo();

        String bankName = "우리은행";
        int fromBankAccountNumber = 1234;
        InsertBankAccountRequest insertBankAccountRequest = InsertBankAccountRequest.of(bankName, fromBankAccountNumber);

        Bank bank = this.createBank(bankName);

        // stubbing
        this.createStubbingBankAccount(bank.getBankNo(), bank.getBankName(), fromBankAccountNumber);

        long fromBankAccountNo = bankAccountApiService.saveBankAccount(memberNo, bank.getBankNo(), insertBankAccountRequest);

        // when
        BigDecimal transferAmount = BigDecimal.valueOf(10000);
        long toBankNo = bank.getBankNo();
        int toBankAccountNumber = 5678;
        transferTransactionApiService.saveTransferTransaction(
                memberNo,
                fromBankAccountNo,
                toBankNo,
                toBankAccountNumber,
                transferAmount
        );

        // then
        List<TransferTransactionOutPut> transferTransactionList = transferTransactionRepository.selectFirstTransferTransactionListByMemberNo(memberNo);

        assertThat(transferTransactionList).hasSize(1)
                .extracting(
                        "fromMemberNo",
                        "fromBankAccountNo",
                        "fromBankNo",
                        "fromBankName",
                        "fromBankAccountNumber",
                        "toBankNo",
                        "toBankName",
                        "toBankAccountNumber",
                        "transferAmount"
                )
                .usingRecursiveFieldByFieldElementComparator(
                        RecursiveComparisonConfiguration.builder()
                                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class).build()
                )
                .containsExactlyInAnyOrder(
                        tuple(
                                memberNo,
                                fromBankAccountNo,
                                bank.getBankNo(),
                                bank.getBankName(),
                                fromBankAccountNumber,
                                toBankNo,
                                bank.getBankName(),
                                toBankAccountNumber,
                                transferAmount
                        )
                );
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