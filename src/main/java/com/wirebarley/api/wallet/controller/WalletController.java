package com.wirebarley.api.wallet.controller;

import com.wirebarley.api.wallet.model.request.WalletDepositRequest;
import com.wirebarley.api.wallet.model.request.WalletWithdrawRequest;
import com.wirebarley.api.wallet.model.response.WalletBalanceResponse;
import com.wirebarley.api.wallet.model.response.WalletTransactionResponse;
import com.wirebarley.api.wallet.model.response.WalletTransactionResponses;
import com.wirebarley.api.wallet.service.WalletApiService;
import com.wirebarley.domain.wallet.model.response.WalletBalanceOutPut;
import com.wirebarley.domain.wallet.model.response.WalletTransactionOutPut;
import com.wirebarley.domain.wallet.validator.WalletValidator;
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
@RequestMapping("/wallets")
public class WalletController {

    private final WalletApiService walletApiService;
    private final WalletValidator walletValidator;

    /**
     * <h1>월렛 잔액 조회 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @GetMapping("/balance")
    public ResponseEntity<WalletBalanceResponse> selectBalance(@LoginMember JwtMemberRequest jwtMemberRequest) {

        WalletBalanceOutPut walletBalanceOutPut = walletApiService.selectBalance(jwtMemberRequest.getMemberNo());
        return ResponseEntity.ok().body(WalletBalanceResponse.of(walletBalanceOutPut.getBalance()));
    }

    /**
     * <h1>월렛 입금/출금 내역 조회하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @GetMapping("/transaction")
    public ResponseEntity<WalletTransactionResponses> selectWalletTransactionList(@LoginMember JwtMemberRequest jwtMemberRequest) {

        List<WalletTransactionOutPut> walletTransactionList = walletApiService.selectWalletTransactionList(jwtMemberRequest.getMemberNo());
        return ResponseEntity.ok().body(WalletTransactionResponses.of(WalletTransactionResponse.of(walletTransactionList)));
    }

    /**
     * <h1>월렛 입금 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @PostMapping("/bank-accounts/{bankAccountNo}/deposit")
    public ResponseEntity<?> deposit(
            @LoginMember JwtMemberRequest jwtMemberRequest,
            @PathVariable("bankAccountNo") long bankAccountNo,
            @RequestBody WalletDepositRequest walletDepositRequest
    ) {

        walletValidator.validatorWalletDepositBalance(walletDepositRequest);

        walletApiService.deposit(jwtMemberRequest.getMemberNo(), bankAccountNo, walletDepositRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * <h1>월렛 출금 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @PostMapping("/bank-accounts/{bankAccountNo}/withdraw")
    public ResponseEntity<?> withdraw(
            @LoginMember JwtMemberRequest jwtMemberRequest,
            @PathVariable("bankAccountNo") long bankAccountNo,
            @RequestBody WalletWithdrawRequest walletWithdrawRequest
    ) {

        walletValidator.validatorWalletWithdrawBalance(walletWithdrawRequest);

        walletApiService.withdraw(jwtMemberRequest.getMemberNo(), bankAccountNo, walletWithdrawRequest);
        return ResponseEntity.noContent().build();
    }
}
