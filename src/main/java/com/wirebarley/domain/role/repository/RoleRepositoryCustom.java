package com.wirebarley.domain.role.repository;

import java.util.List;

public interface RoleRepositoryCustom {


    List<String> selectRoleNameListByMemberNo(Long memberNo);
}
