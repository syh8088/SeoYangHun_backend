package com.wirebarley.api.member.service;

import com.wirebarley.api.member.model.request.SaveMemberRequest;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.model.request.SaveMemberInPut;
import com.wirebarley.domain.member.service.MemberService;
import com.wirebarley.domain.role.model.entity.Role;
import com.wirebarley.domain.role.service.RoleService;
import com.wirebarley.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberApiService {

    private final MemberService memberService;
    private final RoleService roleService;
    private final WalletService walletService;

    /**
     * <h1>계정 회원 가입</h1>
     * <pre>
     * - 해당 계정 Role 등록 및 계정 DB 저장 </br>
     * - ROLE 은 총 4가지 타입이 있습니다. </br>
     *
     * 1. ROLE_SUPER_ADMIN
     * 2. ROLE_ADMIN
     * 3. ROLE_CLIENT
     * 4. ROLE_USER
     *
     * 여기서는 간단하게 `ROLE_USER` 으로 default 로 매핑 하도록 하겠습니다.
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public void saveMember(SaveMemberRequest saveMemberRequest) {

        SaveMemberInPut saveMemberInPut
                = SaveMemberInPut.of(
                    saveMemberRequest.getId(),
                    saveMemberRequest.getPassword(),
                    saveMemberRequest.getName()
                );

        Role role = roleService.selectDefaultRole();
        Member member = memberService.saveMember(saveMemberInPut, role);
        walletService.saveWallet(member);
    }
}
