package com.wirebarley.domain.role.repository;

import com.wirebarley.domain.role.model.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {

}