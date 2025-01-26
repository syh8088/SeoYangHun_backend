package com.wirebarley.domain.transfertransaction.validator;

import com.wirebarley.domain.transfertransaction.repository.TransferTransactionRepository;
import com.wirebarley.global.exception.errorCode.TransferTransactionErrorCode;
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
public class TransferTransactionValidator {

    private final BigDecimal DAILY_TRANSFER_LIMIT = BigDecimal.valueOf(3_000_000);

    private final DecimalFormat FORMATTER = new DecimalFormat("###,###");

    private final TransferTransactionRepository transferTransactionRepository;

    /**
     * <h1>이체 유효성 검사하기</h1>
     * <pre>
     *     - 1일 출금 한도: 300 만 원 유효성 검사
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    public void saveTransferTransaction(long memberNo) {

        LocalDateTime startDatetime = TimeConverter.nowStartDateTime();
        LocalDateTime endDatetime = TimeConverter.nowEndDateTime();
        BigDecimal dailyTransfer
                = transferTransactionRepository.selectDailyTransferByMemberNo(memberNo, startDatetime, endDatetime);

        if (!Objects.isNull(dailyTransfer) && DAILY_TRANSFER_LIMIT.compareTo(dailyTransfer) < 0) {
            throw new BusinessException(TransferTransactionErrorCode.DAILY_TRANSFER_LIMIT, new Object[]{ FORMATTER.format(DAILY_TRANSFER_LIMIT.longValue()) });
        }
    }
}
