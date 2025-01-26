package com.wirebarley.api.transfertransaction.service;

import com.wirebarley.api.transfertransaction.model.request.SelectTransferTransactionListRequest;
import com.wirebarley.domain.bank.model.response.BankOutPut;
import com.wirebarley.domain.bank.service.BankService;
import com.wirebarley.domain.bankaccount.model.response.BankAccountWithBankOutPut;
import com.wirebarley.domain.bankaccount.service.BankAccountAdapter;
import com.wirebarley.domain.bankaccount.service.BankAccountService;
import com.wirebarley.domain.transfertransaction.model.request.SelectTransferTransactionInPut;
import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import com.wirebarley.domain.transfertransaction.service.TransferTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferTransactionApiService {

    private final BankAccountService bankAccountService;
    private final BankService bankService;
    private final TransferTransactionService transferTransactionService;

    private final List<BankAccountAdapter> bankAccountAdapters;

    /**
     * <h1>계좌 이체 합니다.</h1>
     * <pre>
     * - 계정 조회 하기
     * - 특정 은행 서버로 은행 정보 존재 여부 및 은행 계좌 정보 가져오기
     * - 해당 계정 은행 계좌 등록
     * </pre>
     * @author syh
     * @version 1.0.0
     **/
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveTransferTransaction(long memberNo, long fromBankAccountNo, long toBankNo, int toBankAccountNumber, BigDecimal transferAmount) {

        BankAccountWithBankOutPut bankAccountWithBankOutPut
                = bankAccountService.selectBankAccountThenThrowExceptionByMemberNoAndBankAccountNo(memberNo, fromBankAccountNo);

        BankOutPut toBankOutPut = bankService.selectBankByBankNo(toBankNo);


        BankAccountAdapter handlerBankAccountService = BankAccountAdapter.getHandlerBankAccountServices(bankAccountAdapters);
        handlerBankAccountService.transferTransaction(
                bankAccountWithBankOutPut.getBankName(),
                bankAccountWithBankOutPut.getBankAccountNumber(),
                toBankOutPut.getBankName(),
                toBankAccountNumber,
                transferAmount
        );

        transferTransactionService.saverTransferTransaction(
                memberNo,
                bankAccountWithBankOutPut.getBankAccountNo(),
                toBankOutPut.getBankNo(),
                toBankAccountNumber
        );
    }

    /**
     * <h1>계좌 이체 이력 조회</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    public List<TransferTransactionOutPut> selectTransferTransactionList(long memberNo, SelectTransferTransactionListRequest selectTransferTransactionListRequest) {

        SelectTransferTransactionInPut selectTransferTransactionInPut = SelectTransferTransactionInPut.of(
                memberNo,
                selectTransferTransactionListRequest.getTransferTransactionNo(),
                selectTransferTransactionListRequest.getCreatedDateTime(),
                selectTransferTransactionListRequest.getLimit()
        );

        return transferTransactionService.selectTransferTransactionList(selectTransferTransactionInPut);
    }
}
