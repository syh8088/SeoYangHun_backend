package com.wirebarley.global.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
public class PrincipalDetails implements UserDetails {

    private MemberDto memberDto;
    private List<GrantedAuthority> roles;

    @Builder
    private PrincipalDetails(MemberDto memberDto, List<GrantedAuthority> roles) {
      this.memberDto = memberDto;
      this.roles = roles;
    }

    public static PrincipalDetails of(MemberDto memberDto, List<GrantedAuthority> roles) {
        return PrincipalDetails.builder()
                .memberDto(memberDto)
                .roles(roles)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDto.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
