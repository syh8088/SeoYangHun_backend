package com.wirebarley.global.securiy.service;

import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.domain.role.repository.RoleRepository;
import com.wirebarley.global.exception.errorCode.MemberErrorCode;
import com.wirebarley.global.exception.exception.UnauthorizedException;
import com.wirebarley.global.model.response.MemberDto;
import com.wirebarley.global.model.response.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findById(username);
        if (member == null) {
            throw new UnauthorizedException(MemberErrorCode.USER_NAME_NOT_FOUND);
        }

        List<String> roleNameList = roleRepository.selectRoleNameListByMemberNo(member.getMemberNo());

        List<GrantedAuthority> authorities = roleNameList
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        MemberDto memberDto = MemberDto.of(
                member.getMemberNo(),
                member.getId(),
                member.getPassword(),
                member.getName()
        );

        return PrincipalDetails.of(memberDto, authorities);
    }
}
