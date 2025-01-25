package com.wirebarley.global.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtMemberRequest {

    private long memberNo;
    private String memberId;
    private String name;

    @Builder
    private JwtMemberRequest(long memberNo, String memberId, String name) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.name = name;
    }

    public static JwtMemberRequest of(Long memberNo, String id, String name) {
        return JwtMemberRequest.builder()
                .memberNo(memberNo)
                .memberId(id)
                .name(name)
                .build();
    }

}