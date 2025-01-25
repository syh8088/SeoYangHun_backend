package com.wirebarley.api.authentication.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorizationRequest {

    private String id;
    private String password;

    @Builder
    private AuthorizationRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public static AuthorizationRequest of(String id, String password) {
        return AuthorizationRequest.builder()
                .id(id)
                .password(password)
                .build();
    }
}
