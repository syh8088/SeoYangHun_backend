package com.wirebarley.api.member.service;

import com.wirebarley.IntegrationTestSupport;
import com.wirebarley.api.member.model.request.SaveMemberRequest;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.domain.member.service.MemberService;
import com.wirebarley.domain.wallet.repository.WalletRepository;
import com.wirebarley.global.util.Snowflake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberApiServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MemberApiService memberApiService;

    @Autowired
    private MemberService memberService;

    private final Snowflake snowflake = new Snowflake();

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        walletRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입 테스트 입니다.")
    void memberJoin() {
        // given
        // when
        String memberId = "test";
        String name = "홍길동";
        SaveMemberRequest saveMemberRequest = SaveMemberRequest.of(memberId, "1234", name);
        memberApiService.saveMember(saveMemberRequest);

        // then
        Member member = memberService.selectMemberEntityThenThrowExceptionByMemberId(memberId);
        assertThat(member).isNotNull();
        assertThat(member.getMemberNo()).isNotNull();
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isNotNull();
        assertThat(member.getPassword()).isNotNull();

        assertThat(member.getId()).isEqualTo(memberId);
        assertThat(member.getName()).isEqualTo(name);
    }

}