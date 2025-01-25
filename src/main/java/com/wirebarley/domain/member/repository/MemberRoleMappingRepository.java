package com.wirebarley.domain.member.repository;

import com.wirebarley.domain.member.model.entity.MemberRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleMappingRepository extends JpaRepository<MemberRoleMapping, Long> {

}