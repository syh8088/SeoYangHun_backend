package com.wirebarley.api.transfertransaction.model.response;

import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TransferTransactionResponses {

    private List<TransferTransactionResponse> transferTransactionList;

    @Builder
    private TransferTransactionResponses(List<TransferTransactionResponse> transferTransactionList) {
        this.transferTransactionList = transferTransactionList;
    }

    public static TransferTransactionResponses of(List<TransferTransactionOutPut> transferTransactionOutPutList) {

        List<TransferTransactionResponse> transferTransactionList = TransferTransactionResponse.of(transferTransactionOutPutList);
        return TransferTransactionResponses.builder()
                .transferTransactionList(transferTransactionList)
                .build();
    }
}
