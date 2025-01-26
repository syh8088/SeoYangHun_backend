package com.wirebarley.domain.transfertransaction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wirebarley.domain.bank.model.entity.QBank;
import com.wirebarley.domain.bankaccount.model.entity.QBankAccount;
import com.wirebarley.domain.member.model.entity.QMember;
import com.wirebarley.domain.resource.model.response.QResourceWithRoleOutPut;
import com.wirebarley.domain.transfertransaction.model.entity.QTransferTransaction;
import com.wirebarley.domain.transfertransaction.model.response.QTransferTransactionOutPut;
import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
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


	@Override
	public List<TransferTransactionOutPut> selectFirstTransferTransactionListByMemberNo(long memberNo) {
		return queryFactory
				.select(
						new QTransferTransactionOutPut(
								qTransferTransaction.transferTransactionNo,
								qTransferTransaction.fromMember.memberNo,
								qTransferTransaction.fromBankAccount.bankAccountNo,
								qTransferTransaction.fromBankAccount.bank.bankNo,
								qTransferTransaction.fromBankAccount.bank.bankName,
								qTransferTransaction.fromBankAccount.bankAccountNumber,
								qTransferTransaction.toBank.bankNo,
								qTransferTransaction.toBank.bankName,
								qTransferTransaction.toBankAccountNumber,
								qTransferTransaction.createdDateTime,
								qTransferTransaction.transferAmount
						)
				)
				.from(qTransferTransaction)
				.innerJoin(qBankAccount)
				.on(qTransferTransaction.fromBankAccount.eq(qBankAccount))
				.innerJoin(qBank)
				.on(qTransferTransaction.fromBankAccount.bank.eq(qBank))
				.innerJoin(qBank)
				.on(qTransferTransaction.toBank.eq(qBank))
				.innerJoin(qMember)
				.on(qTransferTransaction.fromMember.eq(qMember))
				.fetch();
	}
}



