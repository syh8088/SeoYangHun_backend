package com.wirebarley.global.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberDto {

    private Long memberNo;
    private String id;
    private String password;
    private String name;
    private List<String> roles;

    @Builder
    private MemberDto(Long memberNo, String id, String password, String name) {
        this.memberNo = memberNo;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public static MemberDto of(long memberNo, String id, String password, String name) {
        return MemberDto.builder()
                .memberNo(memberNo)
                .id(id)
                .password(password)
                .name(name)
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}