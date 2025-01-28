package com.wirebarley.global.util;

import java.math.BigDecimal;
import java.util.Objects;

public class FeeUtil {

    private static final BigDecimal TRANSFER_FEE = BigDecimal.valueOf(1);
    private static final BigDecimal CALCULATED_TRANSFER_FEE = TRANSFER_FEE.divide(BigDecimal.valueOf(100));

    public static BigDecimal getTransferFee() {
        return TRANSFER_FEE;
    }

    /**
     * 수수료 금액 = 거래금액 × 수수료율(%) ÷ 100
     *
     * @param transferAmount: 이제 금액
     * @return
     */
    public static BigDecimal calculateTransferFee(BigDecimal transferAmount) {

        if (Objects.isNull(transferAmount)) {
            return BigDecimal.ZERO;
        }

        return transferAmount.multiply(CALCULATED_TRANSFER_FEE);
    }
}
