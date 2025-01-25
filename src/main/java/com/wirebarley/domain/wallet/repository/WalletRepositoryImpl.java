package com.wirebarley.domain.wallet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.bank.model.entity.QBank;
import com.wirebarley.domain.member.model.entity.QMember;
import com.wirebarley.domain.wallet.model.entity.QWallet;
import com.wirebarley.domain.wallet.model.entity.QWalletTransaction;
import com.wirebarley.domain.wallet.model.response.*;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class WalletRepositoryImpl implements WalletRepositoryCustom {

	QWallet qWallet = QWallet.wallet;
	QWalletTransaction qWalletTransaction = QWalletTransaction.walletTransaction;
	QMember qMember = QMember.member;
	QBank qBank = QBank.bank;

	private final JPAQueryFactory queryFactory;

	public WalletRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<WalletOutPut> selectWalletByMemberNo(long memberNo) {
		return Optional.ofNullable(queryFactory
				.select(
						new QWalletOutPut(
								qWallet.walletNo,
								qWallet.balance
						)
				)
				.from(qWallet)
				.innerJoin(qMember)
				.on(qWallet.member.eq(qMember))
				.where(qMember.memberNo.eq(memberNo))
				.fetchOne());
	}

	@Override
	public Optional<WalletWithMemberOutPut> selectWalletWithMemberByMemberNo(long memberNo) {
		return Optional.ofNullable(queryFactory
				.select(
						new QWalletWithMemberOutPut(
								qMember.memberNo,
								qWallet.walletNo,
								qWallet.balance
						)
				)
				.from(qWallet)
				.innerJoin(qMember)
				.on(qWallet.member.eq(qMember))
				.where(qMember.memberNo.eq(memberNo))
				.fetchOne());
	}

	@Override
	public Optional<BigDecimal> selectBalanceByMemberNo(long memberNo) {
		return Optional.ofNullable(queryFactory
				.select(
						qWallet.balance
				)
				.from(qWallet)
				.innerJoin(qMember)
				.on(qWallet.member.eq(qMember))
				.where(qMember.memberNo.eq(memberNo))
				.fetchOne());
	}

	@Override
	public List<WalletTransactionOutPut> selectWalletTransactionList(long walletNo) {
		return queryFactory
				.select(
						new QWalletTransactionOutPut(
								qWallet.walletNo,
								qWalletTransaction.walletTransactionNo,
								qBank.bankNo,
								qBank.bankName,
								qWalletTransaction.bankAccountNumber,
								qWalletTransaction.amount,
								qWalletTransaction.type,
								qWalletTransaction.createdDateTime
						)
				)
				.from(qWallet)
				.innerJoin(qWalletTransaction)
				.on(qWalletTransaction.wallet.eq(qWallet))
				.innerJoin(qBank)
				.on(qWalletTransaction.bank.eq(qBank))
				.where(qWallet.walletNo.eq(walletNo))
				.orderBy(qWalletTransaction.createdDateTime.desc())
				.fetch();
	}
}



