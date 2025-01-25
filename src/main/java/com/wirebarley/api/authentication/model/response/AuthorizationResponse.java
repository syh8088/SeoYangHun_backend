package com.wirebarley.api.authentication.model.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationResponse {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String memberId;
    private Long memberNo;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    private AuthorizationResponse(
            String accessToken, String refreshToken, Long expiresIn, String memberId, Long memberNo,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.memberId = memberId;
        this.memberNo = memberNo;
        this.authorities = authorities;
    }

    public static AuthorizationResponse of(String accessToken, String refreshToken, Long expiresIn, String memberId, Collection<? extends GrantedAuthority> authorities) {
        return AuthorizationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .memberId(memberId)
                .authorities(authorities)
                .build();
    }
}
