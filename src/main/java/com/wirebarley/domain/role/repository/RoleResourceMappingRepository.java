package com.wirebarley.domain.role.repository;

import com.wirebarley.domain.role.model.entity.RoleResourceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleResourceMappingRepository extends JpaRepository<RoleResourceMapping, Long> {

}