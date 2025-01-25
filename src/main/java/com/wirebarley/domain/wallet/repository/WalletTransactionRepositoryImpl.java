package com.wirebarley.domain.wallet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class WalletTransactionRepositoryImpl implements WalletTransactionRepositoryCustom {


	private final JPAQueryFactory queryFactory;

	public WalletTransactionRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

}



