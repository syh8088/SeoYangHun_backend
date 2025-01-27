package com.wirebarley.mock;

import com.wirebarley.global.model.response.MemberDto;
import com.wirebarley.global.model.response.PrincipalDetails;
import com.wirebarley.global.token.RestAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {

        String id = annotation.id();
        String role = annotation.role();

        MemberDto memberDto = MemberDto.of(1, id, "1234", "test");

        List<GrantedAuthority> authorities = Stream.of(role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        PrincipalDetails principalDetails = PrincipalDetails.of(memberDto, authorities);

        RestAuthenticationToken authenticationToken
                = new RestAuthenticationToken(principalDetails.getAuthorities(), principalDetails, null);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}