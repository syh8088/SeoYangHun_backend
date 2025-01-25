package com.wirebarley.domain.role.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.member.model.entity.QMember;
import com.wirebarley.domain.member.model.entity.QMemberRoleMapping;
import com.wirebarley.domain.role.model.entity.QRole;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RoleRepositoryImpl implements RoleRepositoryCustom {

	QRole qRole = QRole.role;
	QMemberRoleMapping qMemberRoleMapping = QMemberRoleMapping.memberRoleMapping;
	QMember qMember = QMember.member;

	private final JPAQueryFactory queryFactory;

	public RoleRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}


	@Override
	public List<String> selectRoleNameListByMemberNo(Long memberNo) {
		return queryFactory
				.select(
						qRole.roleName
				)
				.from(qMember)
				.innerJoin(qMemberRoleMapping)
				.on(qMemberRoleMapping.member.eq(qMember))
				.innerJoin(qRole)
				.on(qMemberRoleMapping.role.eq(qRole))
				.where(qMember.memberNo.eq(memberNo))
				.fetch();
	}
}



