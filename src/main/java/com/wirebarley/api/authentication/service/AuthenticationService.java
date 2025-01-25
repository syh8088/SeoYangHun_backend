package com.wirebarley.api.authentication.service;

import com.wirebarley.api.authentication.model.response.AuthorizationResponse;
import com.wirebarley.global.jwt.JwtTokenProvider;
import com.wirebarley.global.model.response.PrincipalDetails;
import com.wirebarley.global.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    /**
     * <h1>AccessToken 생성</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public String generateAccessToken(String username) {
        return jwtTokenProvider.generateAccessToken(username);
    }

    /**
     * <h1>RefreshToken 생성</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public String generateRefreshToken(String username) {
        return jwtTokenProvider.generateRefreshToken(username);
    }

    public AuthorizationResponse createAuthorizationResponse(
            String accessToken, String refreshToken, PrincipalDetails principalDetails
    ) {
        return AuthorizationResponse.of(
                accessToken,
                refreshToken,
                jwtProperties.getAccessTokenExpired(),
                principalDetails.getUsername(),
                principalDetails.getAuthorities()
        );
    }

}
