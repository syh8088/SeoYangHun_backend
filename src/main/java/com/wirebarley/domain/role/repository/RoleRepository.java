package com.wirebarley.domain.role.repository;

import com.wirebarley.domain.role.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {

    @Query("select r from Role AS r where r.roleName = 'ROLE_USER'")
    Role selectDefaultRole();
}