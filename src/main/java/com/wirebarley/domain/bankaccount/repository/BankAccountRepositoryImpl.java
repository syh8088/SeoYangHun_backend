package com.wirebarley.domain.bankaccount.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.bank.model.entity.QBank;
import com.wirebarley.domain.bankaccount.model.entity.QBankAccount;
import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;
import com.wirebarley.domain.bankaccount.model.response.QBankAccountWithBankOutPut;
import com.wirebarley.domain.member.model.entity.QMember;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class BankAccountRepositoryImpl implements BankAccountRepositoryCustom {

	QBankAccount qBankAccount = QBankAccount.bankAccount;
	QBank qBank = QBank.bank;
	QMember qMember = QMember.member;

	private final JPAQueryFactory queryFactory;

	public BankAccountRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNo(long memberNo) {
		return Optional.ofNullable(queryFactory
                .select(
                        new QBankAccountWithBankOutPut(
                                qBankAccount.bankAccountNo,
                                qBank.bankNo,
                                qBank.bankName,
                                qBankAccount.bankAccountNumber
                        )
                )
                .from(qBankAccount)
                .innerJoin(qBank)
                .on(qBankAccount.bank.eq(qBank))
                .innerJoin(qMember)
                .on(qBankAccount.member.eq(qMember))
                .where(qMember.memberNo.eq(memberNo))
                .fetchOne());
	}

	@Override
	public boolean existsBankAccountByMemberNoAndBankNo(long memberNo, long bankNo) {

		Integer fetchOne = queryFactory
				.selectOne()
				.from(qBankAccount)
				.innerJoin(qMember)
				.on(qBankAccount.member.eq(qMember))
				.where(qMember.memberNo.eq(memberNo).and(qBank.bankNo.eq(bankNo)))
				.fetchFirst();

		return fetchOne != null;
	}
}



