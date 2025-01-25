package com.wirebarley.domain.resource.model.entity;

import com.wirebarley.domain.resource.enums.HttpMethod;
import com.wirebarley.domain.resource.enums.ResourceType;
import com.wirebarley.domain.role.model.entity.RoleResourceMapping;
import com.wirebarley.global.model.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resources")
public class Resource extends CommonEntity {

    @Id
    @Column(name = "resource_no")
    private Long resourceNo;

    @Column(name = "resource_name")
    private String resourceName;

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method")
    private HttpMethod httpMethod;

    @Column(name = "order_num")
    private int orderNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resource", cascade = CascadeType.ALL)
    private List<RoleResourceMapping> roleResourceMappings = new ArrayList<>();
}