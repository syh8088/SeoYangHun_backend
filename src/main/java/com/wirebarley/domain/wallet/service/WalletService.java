package com.wirebarley.domain.wallet.service;

import com.wirebarley.domain.bank.model.entity.Bank;
import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.wallet.enums.WalletTransactionType;
import com.wirebarley.domain.wallet.model.entity.Wallet;
import com.wirebarley.domain.wallet.model.entity.WalletTransaction;
import com.wirebarley.domain.wallet.model.response.WalletBalanceOutPut;
import com.wirebarley.domain.wallet.model.response.WalletOutPut;
import com.wirebarley.domain.wallet.model.response.WalletTransactionOutPut;
import com.wirebarley.domain.wallet.model.response.WalletWithMemberOutPut;
import com.wirebarley.domain.wallet.repository.WalletRepository;
import com.wirebarley.domain.wallet.repository.WalletTransactionRepository;
import com.wirebarley.global.exception.errorCode.WalletErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import com.wirebarley.global.util.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    private final Snowflake snowflake = new Snowflake();

    /**
     * <h1>월렛 잔액 조회하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(readOnly = true)
    public WalletBalanceOutPut selectBalance(long memberNo) {

        BigDecimal balance = walletRepository.selectBalanceByMemberNo(memberNo)
                .orElseThrow(() ->  new BusinessException(WalletErrorCode.NOT_FOUND_WALLET));

        return WalletBalanceOutPut.of(balance);
    }

    /**
     * <h1>월렛 입금/출금 거래 내역 조회하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(readOnly = true)
    public List<WalletTransactionOutPut> selectWalletTransactionList(long walletNo) {
        return walletRepository.selectWalletTransactionList(walletNo);
    }

    /**
     * <h1>Wallet 등록 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public void saveWallet(Member member) {

        Wallet wallet = Wallet.of(snowflake.nextId(), member);
        walletRepository.save(wallet);
    }

    /**
     * <h1>Wallet 입금 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public void updateWalletDeposit(
            long walletNo,
            long bankNo,
            int bankAccountNumber,
            WalletTransactionType walletTransactionType,
            BigDecimal depositAmount
    ) {

        WalletTransaction walletTransaction = this.createWalletTransaction(
                walletNo,
                bankNo,
                bankAccountNumber,
                depositAmount,
                walletTransactionType
        );

        walletTransactionRepository.save(walletTransaction);
        walletRepository.updateDepositBalanceByWalletNo(walletNo, depositAmount);
    }

    /**
     * <h1>Wallet 출금 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public void updateWalletWithdraw(
            long walletNo,
            long bankNo,
            int bankAccountNumber,
            WalletTransactionType walletTransactionType,
            BigDecimal withdrawAmount
    ) {

        withdrawAmount = this.createNegativeAmount(withdrawAmount);

        WalletTransaction walletTransaction = this.createWalletTransaction(
                walletNo,
                bankNo,
                bankAccountNumber,
                withdrawAmount,
                walletTransactionType
        );

        walletTransactionRepository.save(walletTransaction);
        walletRepository.updateWithdrawBalanceByWalletNo(walletNo, withdrawAmount);
    }

    /**
     * <h1>쿠폰 주문 환불시 월렛 금액 환불 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional
    public void updateRefundBalance(long walletNo, BigDecimal refundAmount) {

        walletRepository.updateRefundBalance(walletNo, refundAmount);
    }

    @Transactional
    public void updateWalletOrder(long walletNo, BigDecimal amount) {
        walletRepository.updateBalanceOrderByWalletNo(walletNo, amount);
    }

    @Transactional(readOnly = true)
    public WalletOutPut selectWalletThenThrowByMemberNo(long memberNo) {
        return walletRepository.selectWalletByMemberNo(memberNo)
                .orElseThrow(() ->  new BusinessException(WalletErrorCode.NOT_FOUND_WALLET));
    }

    /**
     * <h1>해당 회원 Wallet 데이터 출력 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(readOnly = true)
    public WalletWithMemberOutPut selectWalletWithMemberThenThrowExceptionByMemberNo(long memberNo) {
        return walletRepository.selectWalletWithMemberByMemberNo(memberNo)
                .orElseThrow(() ->  new BusinessException(WalletErrorCode.NOT_FOUND_WALLET));
    }

    /**
     * <h1>Wallet 이력 등록 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    private WalletTransaction createWalletTransaction(
            long walletNo,
            long bankNo,
            int bankAccountNumber,
            BigDecimal amount,
            WalletTransactionType walletTransactionType
    ) {

        Wallet wallet = Wallet.of(walletNo);
        Bank bank = Bank.of(bankNo);
        return WalletTransaction.of(
                snowflake.nextId(),
                wallet,
                bank,
                bankAccountNumber,
                amount,
                walletTransactionType
        );
    }

    /**
     * <h1>금액 음수로 변환 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    private BigDecimal createNegativeAmount(BigDecimal withdrawAmount) {
        BigDecimal minusQty = new BigDecimal(-1);
        withdrawAmount = withdrawAmount.multiply(minusQty);
        return withdrawAmount;
    }



}
