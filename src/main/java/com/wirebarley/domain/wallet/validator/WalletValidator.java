package com.wirebarley.domain.wallet.validator;

import com.wirebarley.api.wallet.model.request.WalletDepositRequest;
import com.wirebarley.api.wallet.model.request.WalletWithdrawRequest;
import com.wirebarley.domain.wallet.enums.WalletTransactionType;
import com.wirebarley.domain.wallet.repository.WalletTransactionRepository;
import com.wirebarley.global.exception.errorCode.WalletErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import com.wirebarley.global.util.TimeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletValidator {


    private final WalletTransactionRepository walletTransactionRepository;

    private final BigDecimal DAILY_WITHDRAWAL_LIMIT  = BigDecimal.valueOf(30_000_000);

    private final DecimalFormat FORMATTER = new DecimalFormat("###,###");

    /**
     * <h1>월렛 입금 유효성 요청 데이터  검사하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public void validatorWalletDepositBalance(WalletDepositRequest walletDepositRequest) {
        if (Objects.isNull(walletDepositRequest) || Objects.isNull(walletDepositRequest.getDepositAmount()) || walletDepositRequest.getDepositAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(WalletErrorCode.BALANCE_LESS_THAN_ZERO);
        }
    }

    /**
     * <h1>월렛 출금 유효성 요청 데이터 검사하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public void validatorWalletWithdrawBalance(WalletWithdrawRequest walletWithdrawRequest) {
        if (Objects.isNull(walletWithdrawRequest) || Objects.isNull(walletWithdrawRequest.getWithdrawAmount()) || walletWithdrawRequest.getWithdrawAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(WalletErrorCode.BALANCE_LESS_THAN_ZERO);
        }
    }

    public void checkBalanceExist(BigDecimal savedBalance, BigDecimal amount) {
        if (savedBalance.compareTo(amount) < 0) {
            throw new BusinessException(WalletErrorCode.INSUFFICIENT_BALANCE);
        }
    }


    /**
     * <h1>월렛 출금 유효성 검사하기</h1>
     * <pre>
     *     - 1일 출금 한도: 1,000만 원 유효성 검사
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    public void validationWithdrawalLimit(long walletNo) {

        LocalDateTime startDatetime = TimeConverter.nowStartDateTime();
        LocalDateTime endDatetime = TimeConverter.nowEndDateTime();
        BigDecimal bailyWithdrawalBalance
                = walletTransactionRepository.selectDailyWithdrawalBalanceByWalletNo(walletNo, WalletTransactionType.WITHDRAW, startDatetime, endDatetime);

        if (!Objects.isNull(bailyWithdrawalBalance) && DAILY_WITHDRAWAL_LIMIT.compareTo(bailyWithdrawalBalance) < 0) {
            throw new BusinessException(WalletErrorCode.DAILY_WITHDRAWAL_LIMIT, new Object[]{ FORMATTER.format(DAILY_WITHDRAWAL_LIMIT.longValue()) });
        }
    }
}
