package com.wirebarley.global.securiy.manager;


import com.wirebarley.domain.resource.model.response.ResourceWithHttpMethod;
import com.wirebarley.global.securiy.mapper.UrlRoleMapper;

import java.util.Map;

public class DynamicAuthorizationService {

    private final UrlRoleMapper delegate;

    public DynamicAuthorizationService(UrlRoleMapper delegate) {
        this.delegate = delegate;
    }

    public Map<ResourceWithHttpMethod, String> selectUrlRoleMappings() {
            return delegate.getUrlRoleMappings();
    }
}
