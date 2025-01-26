package com.wirebarley.domain.transfertransaction.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SelectTransferTransactionInPut {

    private long fromMemberNo;
    private Long transferTransactionNo;
    private LocalDateTime createdDateTime;
    private int limit;

    @Builder
    private SelectTransferTransactionInPut(long fromMemberNo, Long transferTransactionNo, LocalDateTime createdDateTime, int limit) {
        this.fromMemberNo = fromMemberNo;
        this.transferTransactionNo = transferTransactionNo;
        this.createdDateTime = createdDateTime;
        this.limit = limit;
    }

    public static SelectTransferTransactionInPut of(long fromMemberNo, Long transferTransactionNo, LocalDateTime createdDateTime, int limit) {
        return SelectTransferTransactionInPut.builder()
                .fromMemberNo(fromMemberNo)
                .transferTransactionNo(transferTransactionNo)
                .createdDateTime(createdDateTime)
                .limit(limit)
                .build();
    }
}
