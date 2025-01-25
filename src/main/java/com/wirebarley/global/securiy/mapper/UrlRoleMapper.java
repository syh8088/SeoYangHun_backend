package com.wirebarley.global.securiy.mapper;

import com.wirebarley.domain.resource.model.response.ResourceWithHttpMethod;

import java.util.Map;

public interface UrlRoleMapper {
    Map<ResourceWithHttpMethod, String> getUrlRoleMappings();
}
