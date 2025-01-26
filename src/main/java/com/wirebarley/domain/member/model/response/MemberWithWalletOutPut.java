package com.wirebarley.domain.member.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberWithWalletOutPut {

    private long memberNo;
    private long walletNo;

    @Builder
    private MemberWithWalletOutPut(long memberNo, long walletNo) {
        this.memberNo = memberNo;
        this.walletNo = walletNo;
    }

    public static MemberWithWalletOutPut of(long memberNo, long walletNo) {
        return MemberWithWalletOutPut.builder()
                .memberNo(memberNo)
                .walletNo(walletNo)
                .build();
    }
}
