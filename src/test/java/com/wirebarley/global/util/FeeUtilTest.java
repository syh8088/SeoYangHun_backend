package com.wirebarley.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FeeUtilTest {

    @Test
    @DisplayName("수수료 계산 TEST 입니다.")
    void feeCalculateTest() {
        // given
        BigDecimal transferTransaction = BigDecimal.valueOf(50000);

        // when
        BigDecimal feeCalculatedAmount = FeeUtil.calculateTransferFee(transferTransaction);

        // then
        assertThat(feeCalculatedAmount).isEqualByComparingTo(BigDecimal.valueOf(500));
    }
}