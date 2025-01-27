package com.wirebarley.api.bank.controller;

import com.wirebarley.api.bank.model.response.BankResponses;
import com.wirebarley.api.bank.service.BankApiService;
import com.wirebarley.domain.bank.model.response.BankOutPut;
import com.wirebarley.global.annotaion.LoginMember;
import com.wirebarley.global.model.request.JwtMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/banks")
public class BankController {

    private final BankApiService bankApiService;

    /**
     * <h1>은행 목록 조회 하기</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @GetMapping
    public ResponseEntity<BankResponses> selectBankList(@LoginMember JwtMemberRequest jwtMemberRequest) {

        List<BankOutPut> bankOutPutList = bankApiService.selectBankList();

        return ResponseEntity.ok().body(BankResponses.of(bankOutPutList));
    }
}
