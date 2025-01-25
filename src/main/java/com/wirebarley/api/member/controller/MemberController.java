package com.wirebarley.api.member.controller;

import com.wirebarley.api.member.model.request.SaveMemberRequest;
import com.wirebarley.api.member.service.MemberApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberApiService memberApiService;

    /**
     * <h1>계정 회원 가입</h1>
     *
     * @author syh
     * @version 1.0.0
     **/
    @PostMapping("/join")
    public ResponseEntity<?> saveMember(@RequestBody SaveMemberRequest saveMemberRequest) {

        memberApiService.saveMember(saveMemberRequest);
        return ResponseEntity.noContent().build();
    }

}
