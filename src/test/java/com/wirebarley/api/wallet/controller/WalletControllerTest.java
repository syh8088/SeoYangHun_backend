package com.wirebarley.api.wallet.controller;

import com.wirebarley.ControllerTestSupport;
import com.wirebarley.api.wallet.model.request.WalletDepositRequest;
import com.wirebarley.api.wallet.model.request.WalletWithdrawRequest;
import com.wirebarley.api.wallet.model.response.WalletBalanceResponse;
import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bankaccount.model.entity.BankAccount;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.wallet.model.response.WalletBalanceOutPut;
import com.wirebarley.mock.WithCustomMockUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletControllerTest extends ControllerTestSupport {

    private long memberNo = 0;
    private long bankNo = 0;
    private long bankAccountNo = 0;

    @BeforeEach
    void setUp() {
        memberNo = 1;
        Member member = Member.of(memberNo, "test", "홍길동", "1234");
        memberRepository.save(member);

        bankNo = snowflake.nextId();
        Bank bank = Bank.of(bankNo, "우리은행");
        bankRepository.save(bank);

        bankAccountNo = snowflake.nextId();
        BankAccount bankAccount = BankAccount.of(bankAccountNo, Member.of(memberNo), bankNo, 1234);
        bankAccountRepository.save(bankAccount);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        bankRepository.deleteAllInBatch();
        bankAccountRepository.deleteAllInBatch();
    }

    @Test
    @WithCustomMockUser
    @DisplayName("월렛 입금 TEST 입니다.")
    void deposit() throws Exception {

        // given
        BigDecimal depositAmount = BigDecimal.valueOf(10000);
        WalletDepositRequest request = WalletDepositRequest.of(depositAmount);

        Mockito.doNothing().when(walletApiService).deposit(any(Long.class), any(Long.class), any(WalletDepositRequest.class));

        // when // then
        mockMvc.perform(
                        post("/wallets/bank-accounts/" + bankAccountNo + "/deposit")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithCustomMockUser
    @DisplayName("월렛 출금 TEST 입니다.")
    void withdraw() throws Exception {

        // given
        BigDecimal withdrawAmount = BigDecimal.valueOf(10000);
        WalletWithdrawRequest request = WalletWithdrawRequest.of(withdrawAmount);

        Mockito.doNothing().when(walletApiService).withdraw(any(Long.class), any(Long.class), any(WalletWithdrawRequest.class));

        // when // then
        mockMvc.perform(
                        post("/wallets/bank-accounts/" + bankAccountNo + "/withdraw")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithCustomMockUser
    @DisplayName("월렛 출금 TEST 입니다. - 출금 하고자 하는 금액이 0 이거나 0 보다 작을 경우 Exception 발생")
    void withdrawBalanceLessThanZeroException() throws Exception {

        // given
        BigDecimal withdrawAmount = BigDecimal.ZERO;
        WalletWithdrawRequest request = WalletWithdrawRequest.of(withdrawAmount);

        Mockito.doNothing().when(walletApiService).withdraw(any(Long.class), any(Long.class), any(WalletWithdrawRequest.class));

        // when // then
        mockMvc.perform(
                        post("/wallets/bank-accounts/" + bankAccountNo + "/withdraw")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("WAE0002"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").value("0 보다 작은수로 입력 하셨습니다. 0 보다 큰 수를 입력 해주세요."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithCustomMockUser
    @DisplayName("월렛 잔액 조회 TEST 입니다.")
    void selectBalance() throws Exception {

        // given
        BigDecimal balance = BigDecimal.valueOf(10000);
        WalletBalanceOutPut walletBalanceOutPut = WalletBalanceOutPut.of(balance);
        WalletBalanceResponse walletBalanceResponse = WalletBalanceResponse.of(balance);

        when(walletApiService.selectBalance(any(Long.class))).thenReturn(walletBalanceOutPut);

        // when // then
        mockMvc.perform(
                        get("/wallets/balance")
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.balance").value(balance));
    }

    @Test
    @WithCustomMockUser
    @DisplayName("월렛 입금/출금 내역 조회 TEST 입니다.")
    void selectWalletTransactionList() throws Exception {

        // given
        when(walletApiService.selectWalletTransactionList(any(Long.class))).thenReturn(anyList());

        // when // then
        mockMvc.perform(
                        get("/wallets/transaction")
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}