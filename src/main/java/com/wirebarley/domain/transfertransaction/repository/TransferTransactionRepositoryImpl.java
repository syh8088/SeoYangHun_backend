package com.wirebarley.domain.transfertransaction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.bank.model.entity.QBank;
import com.wirebarley.domain.bankaccount.model.entity.QBankAccount;
import com.wirebarley.domain.member.model.entity.QMember;
import com.wirebarley.domain.transfertransaction.model.entity.QTransferTransaction;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TransferTransactionRepositoryImpl implements TransferTransactionRepositoryCustom {

	QTransferTransaction qTransferTransaction = QTransferTransaction.transferTransaction;
	QBankAccount qBankAccount = QBankAccount.bankAccount;
	QBank qBank = QBank.bank;
	QMember qMember = QMember.member;

	private final JPAQueryFactory queryFactory;

	public TransferTransactionRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}


}



