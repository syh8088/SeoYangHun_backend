package com.wirebarley.api.bank.controller;

import com.wirebarley.ControllerTestSupport;
import com.wirebarley.api.bank.model.response.BankResponses;
import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.bank.model.response.BankOutPut;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.mock.WithCustomMockUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BankControllerTest extends ControllerTestSupport {

    private long bankNo = 0;

    @BeforeEach
    void setUp() {
        Member member = Member.of(snowflake.nextId(), "test", "홍길동", "1234");
        memberRepository.save(member);

        Bank bank = Bank.of(snowflake.nextId(), "우리은행");
        bankNo = bank.getBankNo();
        bankRepository.save(bank);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        bankRepository.deleteAllInBatch();
    }

    @Test
    @WithCustomMockUser
    @DisplayName("은행 목록 조회 하기 TEST 입니다.")
    void selectBankList() throws Exception {

        // given
        List<BankOutPut> bankOutPutList = new ArrayList<>();
        BankOutPut bankAccountOutPut = BankOutPut.of(bankNo, "우리은행");
        bankOutPutList.add(bankAccountOutPut);

        BankResponses bankResponses = BankResponses.of(bankOutPutList);
        when(bankApiService.selectBankList()).thenReturn(bankOutPutList);

        // when // then
        mockMvc.perform(
                        get("/banks")
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.bankList").isArray());
    }
}