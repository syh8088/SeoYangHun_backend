package com.wirebarley.domain.member.model.entity;

import com.wirebarley.domain.member.model.request.SaveMemberInPut;
import com.wirebarley.global.annotaion.Encrypt;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends CommonEntity {

    @Id
    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Encrypt
    @Column(name = "password")
    private String password;

    @Builder
    private Member(long memberNo, String id, String name, String password) {
        this.memberNo = memberNo;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public static Member of(long memberNo, SaveMemberInPut saveMemberInPut) {
        return Member.builder()
                .memberNo(memberNo)
                .id(saveMemberInPut.getId())
                .name(saveMemberInPut.getName())
                .password(saveMemberInPut.getPassword())
                .build();
    }

    public static Member of(long memberNo, String id, String name, String password) {
        return Member.builder()
                .memberNo(memberNo)
                .id(id)
                .name(name)
                .password(password)
                .build();
    }

    public static Member of(long memberNo) {
        return Member.builder()
                .memberNo(memberNo)
                .build();
    }
}