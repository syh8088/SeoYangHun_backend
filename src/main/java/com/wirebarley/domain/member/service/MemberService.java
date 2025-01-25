package com.wirebarley.domain.member.service;

import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.model.entity.MemberRoleMapping;
import com.wirebarley.domain.member.model.request.SaveMemberInPut;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.domain.member.repository.MemberRoleMappingRepository;
import com.wirebarley.domain.role.model.entity.Role;
import com.wirebarley.global.util.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleMappingRepository memberRoleMappingRepository;

    private final Snowflake snowflake = new Snowflake();

    /**
     * <h1>계정 정보 DB 저장</h1>
     * <pre>
     *  `members` 테이블에 등록 및 `member_role_mapping` 테이블에 매핑된 role 정보 등록
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public Member saveMember(SaveMemberInPut saveMemberInPut, Role role) {

        Member member = Member.of(snowflake.nextId(), saveMemberInPut);

        MemberRoleMapping memberRoleMapping = MemberRoleMapping.of(snowflake.nextId(), member, role);
        Member savedMember = memberRepository.save(member);
        memberRoleMappingRepository.save(memberRoleMapping);

        return savedMember;
    }


}
