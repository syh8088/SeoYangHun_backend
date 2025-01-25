package com.wirebarley.domain.role.model.entity;

import com.wirebarley.domain.member.model.entity.MemberRoleMapping;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "roles")
public class Role extends CommonEntity {

    @Id
    @Column(name = "role_no")
    private Long roleNo;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    private List<MemberRoleMapping> memberRoleMappings = new ArrayList<>();

    @Builder
    protected Role(long roleNo, String roleName) {
        this.roleNo = roleNo;
        this.roleName = roleName;
    }

    public static Role of(long roleNo, String roleName) {
        return Role.builder()
                .roleName(roleName)
                .roleNo(roleNo)
                .build();
    }
}