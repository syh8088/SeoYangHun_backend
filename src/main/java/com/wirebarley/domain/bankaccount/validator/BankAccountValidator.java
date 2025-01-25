package com.wirebarley.domain.bankaccount.validator;

import com.wirebarley.api.bankaccount.model.request.InsertBankAccountRequest;
import com.wirebarley.domain.bankaccount.repository.BankAccountRepository;
import com.wirebarley.global.exception.errorCode.BankAccountErrorCode;
import com.wirebarley.global.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class BankAccountValidator {

    private final BankAccountRepository bankAccountRepository;

    /**
     * <h1>은행 계좌 등록시 유효성 검사 합니다.</h1>
     *
     * - 은행명 존재 여부 체크
     * - 계좌번호 존재 여부 체크
     * - 해당 계정 이미 생성된 은행 계좌번호가 있는지 체크 합니다.
     *
     * @author syh
     * @version 1.0.0
     **/
    public void validateSaveBankAccount(long memberNo, long bankNo, InsertBankAccountRequest insertBankAccountRequest) {

        if (Objects.isNull(insertBankAccountRequest) || Objects.isNull(insertBankAccountRequest.getBankName())) {
            throw new BusinessException(BankAccountErrorCode.NOT_EXIST_BANK_NAME);
        }

        if (insertBankAccountRequest.getBankAccountNumber() == 0) {
            throw new BusinessException(BankAccountErrorCode.NOT_EXIST_BANK_ACCOUNT_NUMBER);
        }

        boolean isExistBankAccount = bankAccountRepository.existsBankAccountByMemberNoAndBankNo(memberNo, bankNo);
        if (isExistBankAccount) {
            throw new BusinessException(BankAccountErrorCode.ALREADY_CREATED_BANK_ACCOUNT);
        }
    }
}
