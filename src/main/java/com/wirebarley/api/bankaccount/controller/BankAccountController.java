package com.wirebarley.api.bankaccount.controller;

import com.wirebarley.api.bankaccount.model.request.InsertBankAccountRequest;
import com.wirebarley.api.bankaccount.service.BankAccountApiService;
import com.wirebarley.domain.bankaccount.validator.BankAccountValidator;
import com.wirebarley.global.annotaion.LoginMember;
import com.wirebarley.global.model.request.JwtMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("bank-accounts")
public class BankAccountController {

    private final BankAccountApiService bankAccountApiService;
    private final BankAccountValidator bankAccountValidator;

    /**
     * <h1>은행 계좌 등록</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @PostMapping("/banks/{bankNo}")
    public ResponseEntity<?> saveBankAccount(
            @LoginMember JwtMemberRequest jwtMemberRequest,
            @PathVariable("bankNo") long bankNo,
            @RequestBody InsertBankAccountRequest insertBankAccountRequest
    ) {

        bankAccountValidator.validateSaveBankAccount(jwtMemberRequest.getMemberNo(), bankNo, insertBankAccountRequest);

        bankAccountApiService.saveBankAccount(jwtMemberRequest.getMemberNo(), insertBankAccountRequest);
        return ResponseEntity.noContent().build();
    }
}
