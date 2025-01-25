package com.wirebarley.domain.member.model.entity;

import com.wirebarley.domain.role.model.entity.Role;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_role_mapping")
public class MemberRoleMapping extends CommonEntity {

    @Id
    @Column(name = "member_role_mapping_no")
    private Long memberRoleMappingNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @Column(name = "response_time")
    private LocalDateTime responseTime;

    @Builder
    private MemberRoleMapping(Long memberRoleMappingNo, Member member, Role role, LocalDateTime responseTime) {
      this.memberRoleMappingNo = memberRoleMappingNo;
      this.member = member;
      this.role = role;
      this.responseTime = responseTime;
    }

    public static MemberRoleMapping of(long memberRoleMappingNo, Member member, Role role) {
      return MemberRoleMapping.builder()
              .memberRoleMappingNo(memberRoleMappingNo)
              .member(member)
              .role(role)
              .build();
    }
}