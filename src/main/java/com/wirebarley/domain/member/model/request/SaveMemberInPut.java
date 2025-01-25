package com.wirebarley.domain.member.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveMemberInPut {

    private String id;
    private String password;
    private String name;

    @Builder
    private SaveMemberInPut(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public static SaveMemberInPut of(String id, String password, String name) {
        return SaveMemberInPut.builder()
                .id(id)
                .password(password)
                .name(name)
                .build();
    }
}
