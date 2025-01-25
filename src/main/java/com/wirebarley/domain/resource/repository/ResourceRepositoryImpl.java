package com.wirebarley.domain.resource.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.resource.model.entity.QResource;
import com.wirebarley.domain.resource.model.response.QResourceWithRoleOutPut;
import com.wirebarley.domain.resource.model.response.ResourceWithRoleOutPut;
import com.wirebarley.domain.role.model.entity.QRole;
import com.wirebarley.domain.role.model.entity.QRoleResourceMapping;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ResourceRepositoryImpl implements ResourceRepositoryCustom {

	QResource qResource = QResource.resource;
	QRoleResourceMapping qRoleResourceMapping = QRoleResourceMapping.roleResourceMapping;
	QRole qRole = QRole.role;

	private final JPAQueryFactory queryFactory;

	public ResourceRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}


	@Override
	public List<ResourceWithRoleOutPut> selectAllResourceListWithRole() {
		return queryFactory
				.select(
						new QResourceWithRoleOutPut(
								qResource.resourceName,
								qResource.httpMethod,
								qRole.roleName
						)
				)
				.from(qResource)
				.innerJoin(qRoleResourceMapping)
				.on(qRoleResourceMapping.resource.eq(qResource))
				.innerJoin(qRole)
				.on(qRoleResourceMapping.role.eq(qRole))
				.fetch();
	}
}



