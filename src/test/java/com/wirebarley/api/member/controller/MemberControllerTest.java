package com.wirebarley.api.member.controller;

import com.wirebarley.ControllerTestSupport;
import com.wirebarley.api.member.model.request.SaveMemberRequest;
import com.wirebarley.domain.member.model.entity.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    @BeforeEach
    void setUp() {
        Member member = Member.of(snowflake.nextId(), "test", "홍길동", "1234");
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원 가입 TEST 입니다.")
    void saveMember() throws Exception {

        // given
        SaveMemberRequest request = SaveMemberRequest.of("test2", "1234", "test");

        Mockito.doNothing().when(memberApiService).saveMember(any(SaveMemberRequest.class));

        // when // then
        mockMvc.perform(
                        post("/members/join")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("회원 가입 중복 아이디 Exception 발생 합니다.")
    void alreadyJoinIdException() throws Exception {

        // given
        SaveMemberRequest request = SaveMemberRequest.of("test", "1234", "test");

        Mockito.doNothing().when(memberApiService).saveMember(any(SaveMemberRequest.class));

        // when // then
        mockMvc.perform(
                        post("/members/join")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}