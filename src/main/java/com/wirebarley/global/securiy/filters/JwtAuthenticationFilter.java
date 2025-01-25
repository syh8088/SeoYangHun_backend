package com.wirebarley.global.securiy.filters;

import com.wirebarley.global.jwt.JwtTokenProvider;
import com.wirebarley.global.model.response.PrincipalDetails;
import com.wirebarley.global.securiy.service.CustomUserDetailsService;
import com.wirebarley.global.token.RestAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userServiceHandler;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = null;
        String jwt = null;

        Optional<String> optAccessToken = Optional.ofNullable(request.getHeader("accessToken"));

        if (optAccessToken.isPresent()) {
            jwt = optAccessToken.get();

            username = jwtTokenProvider.extractUsernameByAccessToken(jwt);
        }

        /**
         * 토큰에서 username 을 정상적으로 추출할 수 있고
         * SecurityContextHolder 내에 authentication 객체(이전에 인증된 정보)가 없는 상태인지를 검사한다.
         */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            PrincipalDetails principalDetails = (PrincipalDetails) userServiceHandler.loadUserByUsername(username);

            if (jwtTokenProvider.validateAccessToken(jwt, principalDetails.getUsername())) {

                RestAuthenticationToken authenticationToken
                        = new RestAuthenticationToken(principalDetails.getAuthorities(), principalDetails, null);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
