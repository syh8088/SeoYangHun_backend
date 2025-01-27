package com.wirebarley.api.bankaccount.controller;

import com.wirebarley.ControllerTestSupport;
import com.wirebarley.api.bankaccount.model.request.InsertBankAccountRequest;
import com.wirebarley.api.member.model.request.SaveMemberRequest;
import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bankaccount.model.entity.BankAccount;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.mock.WithCustomMockUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BankAccountControllerTest extends ControllerTestSupport {

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
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        bankRepository.deleteAllInBatch();
    }

    @Test
    @WithCustomMockUser
    @DisplayName("은행 계좌 등록 합니다.")
    void saveBankAccount() throws Exception {

        // given
        InsertBankAccountRequest request = InsertBankAccountRequest.of("우리은행", 1234);

        when(bankAccountApiService.saveBankAccount(
                any(Long.class), any(Long.class), any(InsertBankAccountRequest.class)
        )).thenReturn(0L);

        // when // then
        mockMvc.perform(
                        post("/bank-accounts/banks/" + bankNo)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithCustomMockUser
    @DisplayName("은행 계좌 삭제 합니다.")
    void deleteBankAccount() throws Exception {

        // given
        BankAccount bankAccount = BankAccount.of(snowflake.nextId(), Member.of(memberNo), bankNo, 1234);
        BankAccount savedBankAccount = bankAccountRepository.saveAndFlush(bankAccount);

        // when // then
        mockMvc.perform(
                        delete("/bank-accounts/" + savedBankAccount.getBankAccountNo())
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithCustomMockUser
    @DisplayName("은행 계좌 삭제 합니다. - 등록된 계좌가 존재하지 않는 Exception 발생")
    void deleteBankAccountNotExistBankAccountException() throws Exception {

        // given

        // when // then
        mockMvc.perform(
                        delete("/bank-accounts/" + 1)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("BAE0005"))
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.message").value("등록된 계좌가 존재하지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}