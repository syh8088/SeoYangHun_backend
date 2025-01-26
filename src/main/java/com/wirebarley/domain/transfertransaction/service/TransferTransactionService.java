package com.wirebarley.domain.transfertransaction.service;

import com.wirebarley.domain.transfertransaction.model.entity.TransferTransaction;
import com.wirebarley.domain.transfertransaction.model.request.SelectTransferTransactionInPut;
import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import com.wirebarley.domain.transfertransaction.repository.TransferTransactionMapper;
import com.wirebarley.domain.transfertransaction.repository.TransferTransactionRepository;
import com.wirebarley.global.util.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferTransactionService {

    private final TransferTransactionRepository transferTransactionRepository;
    private final TransferTransactionMapper transferTransactionMapper;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public void saverTransferTransaction(long fromMemberNo, long fromBankAccountNo, long toBankNo, int toBankAccountNumber) {

        TransferTransaction transferTransaction
                = TransferTransaction.of(snowflake.nextId(), fromMemberNo, fromBankAccountNo, toBankNo, toBankAccountNumber);

        transferTransactionRepository.save(transferTransaction);
    }

    /**
     * <h1>이체 내역 조회 합니다.</h1>
     * <pre>
     *  - `transferTransactionNo` 값, 'createdDateTime' 값이 빈값이면 {@link TransferTransactionMapper#selectFirstTransferTransactionListByMemberNo} 로 조회 합니다.
     *  - `transferTransactionNo` 값, 'createdDateTime' 값이 존재하면 {@link TransferTransactionMapper#selectTransferTransactionListByMemberNo} 로 조회 합니다.
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(readOnly = true)
    public List<TransferTransactionOutPut> selectTransferTransactionList(SelectTransferTransactionInPut selectTransferTransactionInPut) {

        if (Objects.isNull(selectTransferTransactionInPut.getTransferTransactionNo()) || Objects.isNull(selectTransferTransactionInPut.getCreatedDateTime())) {
            return transferTransactionMapper.selectFirstTransferTransactionListByMemberNo(
                    selectTransferTransactionInPut.getFromMemberNo(),
                    selectTransferTransactionInPut.getLimit()
            );
        }

        return transferTransactionMapper.selectTransferTransactionListByMemberNo(
                selectTransferTransactionInPut.getFromMemberNo(),
                selectTransferTransactionInPut.getCreatedDateTime(),
                selectTransferTransactionInPut.getTransferTransactionNo(),
                selectTransferTransactionInPut.getLimit()
        );
    }
}
