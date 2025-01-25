package com.wirebarley.domain.resource.repository;


import com.wirebarley.domain.resource.model.response.ResourceWithRoleOutPut;

import java.util.List;

public interface ResourceRepositoryCustom {


    List<ResourceWithRoleOutPut> selectAllResourceListWithRole();

}
