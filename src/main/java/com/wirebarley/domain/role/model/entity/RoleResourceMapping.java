package com.wirebarley.domain.role.model.entity;

import com.wirebarley.domain.resource.model.entity.Resource;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "role_resources_mapping")
public class RoleResourceMapping extends CommonEntity {

    @Id
    @Column(name = "role_resources_mapping_no")
    private long roleResourcesMappingNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_no", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Resource resource;

}
