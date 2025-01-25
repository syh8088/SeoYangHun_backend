package com.wirebarley.global.securiy.mapper;


import com.wirebarley.domain.resource.model.response.ResourceWithHttpMethod;
import com.wirebarley.domain.resource.model.response.ResourceWithRoleOutPut;
import com.wirebarley.domain.resource.repository.ResourceRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PersistentUrlRoleMapper implements UrlRoleMapper {

    private final LinkedHashMap<ResourceWithHttpMethod, String> urlRoleMappings = new LinkedHashMap<>();
    private final ResourceRepository resourceRepository;

    public PersistentUrlRoleMapper(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Map<ResourceWithHttpMethod, String> getUrlRoleMappings() {

        this.urlRoleMappings.clear();

        List<ResourceWithRoleOutPut> resources = resourceRepository.selectAllResourceListWithRole();
        resources.forEach(resourceWithRole -> {

            ResourceWithHttpMethod resourceWithHttpMethod
                    = ResourceWithHttpMethod.of(
                            resourceWithRole.getResourceName(),
                            resourceWithRole.getHttpMethod()
                    );

            urlRoleMappings.put(resourceWithHttpMethod, resourceWithRole.getRoleName());
        });

        return urlRoleMappings;
    }
}
