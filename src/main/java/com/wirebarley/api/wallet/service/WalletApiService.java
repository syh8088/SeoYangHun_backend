package com.wirebarley.api.wallet.service;

import com.wirebarley.api.wallet.model.request.WalletDepositRequest;
import com.wirebarley.api.wallet.model.request.WalletWithdrawRequest;
import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;
import com.wirebarley.domain.bankaccount.service.BankAccountAdapter;
import com.wirebarley.domain.bankaccount.service.BankAccountService;
import com.wirebarley.domain.wallet.enums.WalletTransactionType;
import com.wirebarley.domain.wallet.model.response.WalletBalanceOutPut;
import com.wirebarley.domain.wallet.model.response.WalletTransactionOutPut;
import com.wirebarley.domain.wallet.model.response.WalletWithMemberOutPut;
import com.wirebarley.domain.wallet.service.WalletService;
import com.wirebarley.domain.wallet.validator.WalletValidator;
import com.wirebarley.global.exception.errorCode.BankAccountErrorCode;
import com.wirebarley.global.exception.errorCode.BankErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletApiService {

    private final WalletService walletService;
    private final BankAccountService bankAccountService;
    private final List<BankAccountAdapter> bankAccountAdapters;

    private final WalletValidator walletValidator;

    /**
     * <h1>월렛 잔액 조회하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public WalletBalanceOutPut selectBalance(long memberNo) {
        return walletService.selectBalance(memberNo);
    }

    /**
     * <h1>월렛 입금/출금 거래 내역 조회하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public List<WalletTransactionOutPut> selectWalletTransactionList(long memberNo) {

        WalletWithMemberOutPut walletWithMember = walletService.selectWalletWithMemberThenThrowExceptionByMemberNo(memberNo);
        return walletService.selectWalletTransactionList(walletWithMember.getWalletNo());
    }

    /**
     * <h1>월렛 입금 하기</h1>
     * <pre>
     *     1. 회원 정보 및 wallet 조회
     *     2. 고객의 연동된 계좌가 있는지 체크
     *     3. 실제로 존재하는 은행 계좌인지 체크 합니다.
     *     4. 입금 처리를 합니다.
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void deposit(long memberNo, WalletDepositRequest walletDepositRequest) {

        // 1. 회원 정보 및 wallet 조회
        WalletWithMemberOutPut walletWithMember = walletService.selectWalletWithMemberThenThrowExceptionByMemberNo(memberNo);

        // 2. 고객의 연동된 계좌가 있는지 체크
        BankAccountWithBankOutPut bankAccountWithBank = bankAccountService.selectBankAccountThenThrowExceptionByMemberNo(memberNo);

        // 3. 실제로 존재하는 은행 계좌인지 체크 합니다.
        BankAccountAdapter handlerBankAccountService = BankAccountAdapter.getHandlerBankAccountServices(bankAccountAdapters);
        boolean isBank = handlerBankAccountService.checkBank(bankAccountWithBank.getBankAccountNumber());
        if (!isBank) {
            throw new BusinessException(BankErrorCode.NOT_FOUND_BANK);
        }

        // 4. 입금 처리를 합니다.
        boolean isDeposit = handlerBankAccountService.deposit(walletDepositRequest.getDepositAmount(), bankAccountWithBank.getBankAccountNumber());
        if (!isDeposit) {
            throw new BusinessException(BankAccountErrorCode.NOT_FOUND_BANK_ACCOUNT);
        }

        walletService.updateWalletDeposit(
                walletWithMember.getWalletNo(),
                bankAccountWithBank.getBankNo(),
                bankAccountWithBank.getBankAccountNumber(),
                WalletTransactionType.DEPOSIT,
                walletDepositRequest.getDepositAmount()
        );

    }

    /**
     * <h1>월렛 출금 하기</h1>
     * <pre>
     *     1. 회원 정보 및 wallet 조회
     *     2. 출금 한도 유효성 검사
     *     3. 고객의 연동된 계좌가 있는지 체크
     *     4. 실제로 존재하는 은행 계좌인지 체크 합니다.
     *     5. 출금 처리를 합니다.
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void withdraw(long memberNo, WalletWithdrawRequest walletWithdrawRequest) {

        // 1. 회원 정보 및 wallet 조회
        WalletWithMemberOutPut walletWithMember = walletService.selectWalletWithMemberThenThrowExceptionByMemberNo(memberNo);

        // 2. 출금 한도 유효성 검사
        walletValidator.validationWithdrawalLimit(walletWithMember.getWalletNo(), walletWithdrawRequest.getWithdrawAmount());

        // 3. 고객의 연동된 계좌가 있는지 체크
        BankAccountWithBankOutPut bankAccountWithBank = bankAccountService.selectBankAccountThenThrowExceptionByMemberNo(memberNo);

        // 4. 실제로 존재하는 은행 계좌인지 체크 합니다.
        BankAccountAdapter handlerBankAccountService = BankAccountAdapter.getHandlerBankAccountServices(bankAccountAdapters);
        boolean isBank = handlerBankAccountService.checkBank(bankAccountWithBank.getBankAccountNumber());
        if (!isBank) {
            throw new BusinessException(BankErrorCode.NOT_FOUND_BANK);
        }
        // 5. 출금 처리를 합니다.
        boolean isWithdraw = handlerBankAccountService.withdraw(walletWithdrawRequest.getWithdrawAmount(), bankAccountWithBank.getBankAccountNumber());
        if (!isWithdraw) {
            throw new BusinessException(BankAccountErrorCode.NOT_FOUND_BANK_ACCOUNT);
        }

        walletService.updateWalletWithdraw(
                walletWithMember.getWalletNo(),
                bankAccountWithBank.getBankNo(),
                bankAccountWithBank.getBankAccountNumber(),
                WalletTransactionType.WITHDRAW,
                walletWithdrawRequest.getWithdrawAmount()
        );
    }

}
