package com.wirebarley.api.transfertransaction.controller;

import com.wirebarley.api.transfertransaction.model.request.InsertTransferTransactionRequest;
import com.wirebarley.api.transfertransaction.model.request.SelectTransferTransactionListRequest;
import com.wirebarley.api.transfertransaction.model.response.TransferTransactionResponses;
import com.wirebarley.api.transfertransaction.service.TransferTransactionApiService;
import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import com.wirebarley.domain.transfertransaction.validator.TransferTransactionValidator;
import com.wirebarley.global.annotaion.LoginMember;
import com.wirebarley.global.model.request.JwtMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("transfer-transactions")
public class TransferTransactionController {

    private final TransferTransactionApiService transferTransactionApiService;
    private final TransferTransactionValidator transferTransactionValidator;

    /**
     * <h1>계좌 이체 이력 조회</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @GetMapping
    public ResponseEntity<TransferTransactionResponses> selectTransferTransactionList(
            @LoginMember JwtMemberRequest jwtMemberRequest,
            @ModelAttribute SelectTransferTransactionListRequest selectTransferTransactionListRequest
    ) {

        List<TransferTransactionOutPut> transferTransactionOutPutList
                = transferTransactionApiService.selectTransferTransactionList(jwtMemberRequest.getMemberNo(), selectTransferTransactionListRequest);

        return ResponseEntity.ok().body(TransferTransactionResponses.of(transferTransactionOutPutList));
    }


    /**
     * <h1>계좌 이체 기능</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @PostMapping
    public ResponseEntity<?> saveTransferTransaction(
            @LoginMember JwtMemberRequest jwtMemberRequest,
            @RequestBody InsertTransferTransactionRequest insertTransferTransactionRequest
    ) {

        transferTransactionValidator.saveTransferTransaction(jwtMemberRequest.getMemberNo());

        transferTransactionApiService.saveTransferTransaction(
                jwtMemberRequest.getMemberNo(),
                insertTransferTransactionRequest.getFromBankAccountNo(),
                insertTransferTransactionRequest.getToBankNo(),
                insertTransferTransactionRequest.getToBankAccountNumber(),
                insertTransferTransactionRequest.getTransferAmount()
        );

        return ResponseEntity.noContent().build();
    }

}
