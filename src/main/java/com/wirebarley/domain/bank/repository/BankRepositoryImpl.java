package com.wirebarley.domain.bank.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class BankRepositoryImpl implements BankRepositoryCustom {


	private final JPAQueryFactory queryFactory;

	public BankRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

}



