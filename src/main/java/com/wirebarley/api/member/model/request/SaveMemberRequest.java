package com.wirebarley.api.member.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveMemberRequest {

    private String id;
    private String password;
    private String name;

    @Builder
    private SaveMemberRequest(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public static SaveMemberRequest of(String id, String password, String name) {
        return SaveMemberRequest.builder()
                .id(id)
                .password(password)
                .name(name)
                .build();
    }
}
