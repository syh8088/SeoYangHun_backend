package com.wirebarley.domain.resource.repository;

import com.wirebarley.domain.resource.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>, ResourceRepositoryCustom {

}