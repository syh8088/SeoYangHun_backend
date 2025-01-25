package com.wirebarley.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.member.model.entity.QMember;
import jakarta.persistence.EntityManager;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

	QMember qMember = QMember.member;

	private final JPAQueryFactory queryFactory;

	public MemberRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public boolean existsMemberByMemberId(String id) {

		Integer fetchOne = queryFactory
				.selectOne()
				.from(qMember)
				.where(qMember.id.eq(id))
				.fetchFirst();

		return fetchOne != null;
	}
}



