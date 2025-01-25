package com.wirebarley.global.securiy.handler;

import com.wirebarley.global.annotaion.LoginMember;
import com.wirebarley.global.exception.errorCode.MemberErrorCode;
import com.wirebarley.global.exception.exception.UnauthorizedException;
import com.wirebarley.global.model.request.JwtMemberRequest;
import com.wirebarley.global.model.response.MemberDto;
import com.wirebarley.global.model.response.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginMemberAnnotation = parameter.getParameterAnnotation(LoginMember.class) != null;

        boolean isMemberClass = JwtMemberRequest.class.equals(parameter.getParameterType());
        return isLoginMemberAnnotation && isMemberClass;
    }

    @Override
    public Object resolveArgument (MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /**
         * 아직 인증이 재대로 통과 되지 않고 {@link AnonymousAuthenticationToken} 객체로 반환 되었을때 에러 발생 시킨다.
         */
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.error("LoginMemberArgumentResolver.resolveArgument => 로그인 정보가 존재하지 않음");
            throw new UnauthorizedException(MemberErrorCode.FAIL_LOGIN);
        }

        final PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        if (Objects.isNull(principal.getUsername())) {
            log.error("LoginMemberArgumentResolver.resolveArgument => 로그인 정보가 존재하지 않음");
            throw new UnauthorizedException(MemberErrorCode.FAIL_LOGIN);
        }

        if (authentication.getPrincipal() instanceof PrincipalDetails) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            MemberDto memberDto = principalDetails.getMemberDto();

            return JwtMemberRequest.of(memberDto.getMemberNo(), memberDto.getId(), memberDto.getName());
        }
        else {
            log.error("LoginMemberArgumentResolver.resolveArgument => 로그인 정보가 존재하지 않음");
            throw new UnauthorizedException(MemberErrorCode.FAIL_LOGIN);
        }
    }
}