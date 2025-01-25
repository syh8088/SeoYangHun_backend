package com.wirebarley.domain.role.model.entity;

import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "role_hierarchys")
public class RoleHierarchy extends CommonEntity {

    @Id
    @Column(name = "role_hierarchy_no")
    private Long roleHierarchyNo;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_no", referencedColumnName = "role_hierarchy_no", insertable = false, updatable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private RoleHierarchy parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<RoleHierarchy> children = new HashSet<>();
}
