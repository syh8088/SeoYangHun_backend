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
	public Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNoAndBankAccountNo(long memberNo, long bankAccountNo) {
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
				.innerJoin(qMember)
				.on(qBankAccount.member.eq(qMember))
				.where(qMember.memberNo.eq(memberNo).and(qBankAccount.bankAccountNo.eq(bankAccountNo).and(qBankAccount.isDeleted.eq(false))))
				.fetchOne());
	}

	@Override
	public boolean existsBankAccountByMemberNoAndBankNoAndBankAccountNumberAndIsDeleted(long memberNo, long bankNo, int bankAccountNumber, boolean isDeleted) {

		Integer fetchOne = queryFactory
				.selectOne()
				.from(qBankAccount)
				.innerJoin(qMember)
				.on(qBankAccount.member.eq(qMember))
				.innerJoin(qBank)
				.on(qBankAccount.bank.eq(qBank))
				.where(qMember.memberNo.eq(memberNo).and(qBank.bankNo.eq(bankNo).and(qBankAccount.bankAccountNumber.eq(bankAccountNumber).and(qBankAccount.isDeleted.eq(isDeleted)))))
				.fetchFirst();

		return fetchOne != null;
	}

	@Override
	public boolean existsBankAccountByMemberNoAndBankAccountNo(long memberNo, long bankAccountNo) {

		Integer fetchOne = queryFactory
				.selectOne()
				.from(qBankAccount)
				.innerJoin(qMember)
				.on(qBankAccount.member.eq(qMember))
				.where(qBankAccount.bankAccountNo.eq(bankAccountNo).and(qMember.memberNo.eq(memberNo)))
				.fetchFirst();

		return fetchOne != null;
	}

	@Override
	public Optional<BankAccountWithBankOutPut> selectBankAccountByMemberNoAndBankNoAndBankAccountNumber(long memberNo, long bankAccountNo, int bankAccountNumber) {
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
				.innerJoin(qMember)
				.on(qBankAccount.member.eq(qMember))
				.where(qMember.memberNo.eq(memberNo).and(qBankAccount.bankAccountNo.eq(bankAccountNo).and(qBankAccount.bankAccountNumber.eq(bankAccountNumber))))
				.fetchOne());
	}
}



