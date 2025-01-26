package com.wirebarley.domain.bank.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.bank.model.entity.QBank;
import com.wirebarley.domain.bank.model.response.BankOutPut;
import com.wirebarley.domain.bank.model.response.QBankOutPut;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BankRepositoryImpl implements BankRepositoryCustom {

	QBank qBank = QBank.bank;

	private final JPAQueryFactory queryFactory;

	public BankRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<BankOutPut> selectBankList() {
		return queryFactory
				.select(
						new QBankOutPut(
								qBank.bankNo,
								qBank.bankName
						)
				)
				.from(qBank)
				.fetch();
	}
}


