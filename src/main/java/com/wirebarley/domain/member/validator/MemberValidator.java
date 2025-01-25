package com.wirebarley.domain.member.validator;

import com.wirebarley.api.authentication.model.request.AuthorizationRequest;
import com.wirebarley.api.member.model.request.SaveMemberRequest;
import com.wirebarley.domain.member.repository.MemberRepository;
import com.wirebarley.global.exception.errorCode.MemberErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    /**
     * <h1>로그인시 요청 정보 유효성 검사</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public void validateLogin(AuthorizationRequest authorizationRequest) {
        if (Objects.isNull(authorizationRequest) || Objects.isNull(authorizationRequest.getId())) {
            throw new BusinessException(MemberErrorCode.NOT_EXIST_ID);
        }

        if (Objects.isNull(authorizationRequest.getPassword())) {
            throw new BusinessException(MemberErrorCode.NOT_EXIST_PASSWORD);
        }
    }

    /**
     * <h1>회원가입시 요청 정보 유효성 검사</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public void validateSaveMember(SaveMemberRequest saveMemberRequest) {

        if (Objects.isNull(saveMemberRequest) || Objects.isNull(saveMemberRequest.getId())) {
            throw new BusinessException(MemberErrorCode.NOT_EXIST_ID);
        }

        if (Objects.isNull(saveMemberRequest.getPassword())) {
            throw new BusinessException(MemberErrorCode.NOT_EXIST_PASSWORD);
        }

        boolean isExistMember = memberRepository.existsMemberByMemberId(saveMemberRequest.getId());
        if (isExistMember) {
            throw new BusinessException(MemberErrorCode.ALREADY_JOIN_ID);
        }
    }
}
