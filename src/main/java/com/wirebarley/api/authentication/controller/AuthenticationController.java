package com.wirebarley.api.authentication.controller;

import com.wirebarley.api.authentication.model.request.AuthorizationRequest;
import com.wirebarley.api.authentication.model.response.AuthorizationResponse;
import com.wirebarley.api.authentication.service.AuthenticationService;
import com.wirebarley.domain.member.validator.MemberValidator;
import com.wirebarley.global.exception.errorCode.MemberErrorCode;
import com.wirebarley.global.exception.exception.UnauthorizedException;
import com.wirebarley.global.model.response.PrincipalDetails;
import com.wirebarley.global.token.RestAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("authorize")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    private final MemberValidator memberValidator;

    /**
     * <h1>로그인</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @PostMapping
    public ResponseEntity<AuthorizationResponse> authenticateLogin(@RequestBody AuthorizationRequest authorizationRequest) {

        memberValidator.validateLogin(authorizationRequest);

        try {
            // Spring Security Authentication 객체 생성 하기
            RestAuthenticationToken authenticationToken = new RestAuthenticationToken(authorizationRequest.getId(), authorizationRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            // AccessToken 만들기
            String accessToken = authenticationService.generateAccessToken(principalDetails.getUsername());

            AuthorizationResponse authorizationResponse = authenticationService.createAuthorizationResponse(accessToken, null, principalDetails);

            return ResponseEntity.ok().body(authorizationResponse);
        }
        catch (UnauthorizedException e) {
            throw new UnauthorizedException(MemberErrorCode.USER_NAME_NOT_FOUND);
        }
        catch (AuthenticationException e) {
            throw new UnauthorizedException(MemberErrorCode.AUTHENTICATION_FAILED);
        }
    }

}
