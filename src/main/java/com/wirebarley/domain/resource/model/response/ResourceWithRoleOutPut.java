package com.wirebarley.domain.resource.model.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wirebarley.domain.resource.enums.HttpMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResourceWithRoleOutPut {

    private String resourceName;
    private HttpMethod httpMethod;
    private String roleName;

    @QueryProjection
    public ResourceWithRoleOutPut(String resourceName, HttpMethod httpMethod, String roleName) {
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.roleName = roleName;
    }
}
