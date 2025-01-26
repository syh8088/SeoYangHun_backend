package com.wirebarley.api.transfertransaction.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SelectTransferTransactionListRequest {

    private Long transferTransactionNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;

    private int limit;

    @Builder
    private SelectTransferTransactionListRequest(Long transferTransactionNo, LocalDateTime createdDateTime, int limit) {
        this.transferTransactionNo = transferTransactionNo;
        this.createdDateTime = createdDateTime;
        this.limit = limit;
    }

    public static SelectTransferTransactionListRequest of(Long transferTransactionNo, LocalDateTime createdDateTime, int limit) {
        return SelectTransferTransactionListRequest.builder()
                .transferTransactionNo(transferTransactionNo)
                .createdDateTime(createdDateTime)
                .limit(limit)
                .build();
    }
}
