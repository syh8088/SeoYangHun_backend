package com.wirebarley.global.securiy.filters;

import com.wirebarley.global.exception.errorCode.MemberErrorCode;
import com.wirebarley.global.exception.exception.UnauthorizedException;
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

            try {
                username = jwtTokenProvider.extractUsernameByAccessToken(jwt);
            }
            catch (ExpiredJwtException e) {
                throw new UnauthorizedException(MemberErrorCode.FAIL_LOGIN);
            }
            catch (SignatureException e) {
                log.error("Access Token SignatureException Token = {}, 엑세스 토큰이 만료됨", jwt);
                log.error("JwtAuthenticationFilter.doFilterInternal.SignatureException = {}", e);
            }
            catch (MalformedJwtException e) {
                log.error("Access Token MalformedJwtException Token = {}, 올바르지 않은 엑세스 토큰", jwt);
                log.error("JwtAuthenticationFilter.doFilterInternal.MalformedJwtException = {}", e);
            }
            catch (UnsupportedJwtException e) {
                log.error("Access Token UnsupportedJwtException Token = {}, 지원하지 않는 엑세스 토큰", jwt);
                log.error("JwtAuthenticationFilter.doFilterInternal.UnsupportedJwtException = {}", e);
            }
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
