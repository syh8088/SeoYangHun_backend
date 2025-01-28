package com.wirebarley.api.transfertransaction.controller;

import com.wirebarley.ControllerTestSupport;
import com.wirebarley.api.transfertransaction.model.request.InsertTransferTransactionRequest;
import com.wirebarley.api.transfertransaction.model.request.SelectTransferTransactionListRequest;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.transfertransaction.model.entity.TransferTransaction;
import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import com.wirebarley.mock.WithCustomMockUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransferTransactionControllerTest extends ControllerTestSupport {

    @BeforeEach
    void setUp() {

        Member member = Member.of(1, "test", "홍길동", "1234");
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        transferTransactionRepository.deleteAllInBatch();
    }

    @Test
    @WithCustomMockUser
    @DisplayName("계좌 이체 기능 TEST 입니다.")
    void saveTransferTransaction() throws Exception {

        // given
        BigDecimal transferAmount = BigDecimal.valueOf(10000);
        InsertTransferTransactionRequest request = InsertTransferTransactionRequest.of(1, 1, 1234, transferAmount);

        Mockito.doNothing().when(transferTransactionApiService).saveTransferTransaction(
                any(Long.class),
                any(Long.class),
                any(Long.class),
                any(Integer.class),
                any(BigDecimal.class)
        );

        // when // then
        mockMvc.perform(
                        post("/transfer-transactions")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @WithCustomMockUser
    @DisplayName("계좌 이체 기능 TEST 입니다. - 300만원 한도 Exception 체크")
    void saveTransferTransactionDailyTransferLimitException() throws Exception {

        // given
        TransferTransaction transferTransaction = TransferTransaction.of(snowflake.nextId(), 1, 1, 1, 1234, BigDecimal.valueOf(3_000_000), BigDecimal.valueOf(3_000_000), BigDecimal.valueOf(3_000_000));
        transferTransactionRepository.saveAndFlush(transferTransaction);

        BigDecimal transferAmount = BigDecimal.valueOf(3_000_000);
        InsertTransferTransactionRequest request = InsertTransferTransactionRequest.of(1, 1, 1234, transferAmount);

        Mockito.doNothing().when(transferTransactionApiService).saveTransferTransaction(
                any(Long.class),
                any(Long.class),
                any(Long.class),
                any(Integer.class),
                any(BigDecimal.class)
        );

        // when // then
        mockMvc.perform(
                        post("/transfer-transactions")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("TTE0001"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").value("1일 이체 한도는 3,000,000원 금액 까지만 가능 합니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithCustomMockUser
    @DisplayName("계좌 이체 이력 조회 TEST 입니다.")
    void selectTransferTransactionList() throws Exception {

        // given
        List<TransferTransactionOutPut> transferTransactionOutPutList = new ArrayList<>();
        TransferTransactionOutPut transferTransactionOutPut = TransferTransactionOutPut.of(
                1L, 1L, 1L, 1L, "우리은행", 1234, 1L, "우리은행", 5678, LocalDateTime.now(), BigDecimal.valueOf(1000), BigDecimal.valueOf(1), BigDecimal.valueOf(900)
        );
        transferTransactionOutPutList.add(transferTransactionOutPut);

        when(transferTransactionApiService.selectTransferTransactionList(
                any(Long.class), any(SelectTransferTransactionListRequest.class)
        )).thenReturn(transferTransactionOutPutList);

        // when // then
        mockMvc.perform(
                        get("/transfer-transactions")
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.transferTransactionList").isArray());
    }
}