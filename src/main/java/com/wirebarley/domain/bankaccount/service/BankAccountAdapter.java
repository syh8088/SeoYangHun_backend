package com.wirebarley.domain.bankaccount.service;

import com.wirebarley.domain.bankaccount.model.response.BankAccountOutPut;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountAdapter {

    /**
     * <h1>실제로 외부 은행 서버에 http 통신 통해 은행 정보를 가져와서 고객이 입력한 계좌번호로 은행 계정 정보를 가져옵니다.</h1>
     * </br>
     *
     * 지금은 '우리은행' 밖에 없어서 편의상 조건과 상관없이 "우리은행" 만 출력 하도록 했습니다.</br>
     *
     * 기능을 더 추가해야 된다면 만약 은행 서버가 죽었을때 대비해서 재시도를 통해 정상적인 데이터 응답을 받아 올 수 있도록 해야 합니다. 단 재시도시
     * 재시도가 가능한 Exception 만 구분 해서 재시도를 해야 합니다.
     *
     * @param bankAccountNumber: 은행 계좌 번호
     */
    BankAccountOutPut getBankAccountInfo(int bankAccountNumber);

    /**
     * <h1>실제로 외부 은행 서버에 http 통신 통해 실제로 은행 정보 존재하는지 체크 합니다.</h1>
     * </br>
     *
     * 지금은 '우리은행' 밖에 없어서 편의상 조건과 상관없이 "우리은행" 만 출력 하도록 했습니다.</br>
     *
     * 기능을 더 추가해야 된다면 만약 은행 서버가 죽었을때 대비해서 재시도를 통해 정상적인 데이터 응답을 받아 올 수 있도록 해야 합니다. 단 재시도시
     * 재시도가 가능한 Exception 만 구분 해서 재시도를 해야 합니다.
     *
     * @param bankAccountNumber: 은행 계좌 번호
     */
    boolean checkBank(int bankAccountNumber);

    /**
     * <h1>해당 은행에 있는 특정 고객 계좌 등록된 금액을 '와이어바알리' 가상 계좌에 등록 합니다.</h1>
     * </br>
     *
     * 지금은 '우리은행' 밖에 없어서 편의상 조건과 상관없이 "우리은행" 만 출력 하도록 했습니다.</br>
     *
     * 기능을 더 추가해야 된다면 만약 은행 서버가 죽었을때 대비해서 재시도를 통해 정상적인 데이터 응답을 받아 올 수 있도록 해야 합니다. 단 재시도시
     * 재시도가 가능한 Exception 만 구분 해서 재시도를 해야 합니다.
     *
     * @param depositAmount: wallet 에 입금 하고자 하는 금액
     * @param bankAccountNumber: 은행 계좌 번호
     */
    boolean deposit(BigDecimal depositAmount, int bankAccountNumber);

    /**
     * <h1>'와이어바알리' 가상 계좌에 있는 금액을 해당 은행에 이체 합니다.</h1>
     * </br>
     *
     * 지금은 '우리은행' 밖에 없어서 편의상 조건과 상관없이 "우리은행" 만 출력 하도록 했습니다.</br>
     *
     * 기능을 더 추가해야 된다면 만약 은행 서버가 죽었을때 대비해서 재시도를 통해 정상적인 데이터 응답을 받아 올 수 있도록 해야 합니다. 단 재시도시
     * 재시도가 가능한 Exception 만 구분 해서 재시도를 해야 합니다.
     *
     * @param withdrawAmount: wallet 출금 하고자 하는 금액
     * @param bankAccountNumber: 은행 계좌 번호
     */
    boolean withdraw(BigDecimal withdrawAmount, int bankAccountNumber);

    /**
     * <h1>'와이어바알리' 전용 출금 계좌에서 다른 계좌로 금액을 이체 하는 기능 입니다.
     *
     * - 수수료 계산: 이체 금액의 1%를 수수료로 부과합니다.
     * </br>
     *
     * 지금은 '우리은행' 밖에 없어서 편의상 조건과 상관없이 "우리은행" 만 출력 하도록 했습니다.</br>
     *
     * 기능을 더 추가해야 된다면 만약 은행 서버가 죽었을때 대비해서 재시도를 통해 정상적인 데이터 응답을 받아 올 수 있도록 해야 합니다. 단 재시도시
     * 재시도가 가능한 Exception 만 구분 해서 재시도를 해야 합니다.
     *
     * @param fromBankName:
     * @param fromBankAccountNumber:
     * @param toBankName:
     * @param toBankAccountNumber:
     * @param transferAmount: 이체 금액
     */
    boolean transferTransaction(String fromBankName, int fromBankAccountNumber, String toBankName, int toBankAccountNumber, BigDecimal transferAmount);


    boolean supports();


    static BankAccountAdapter getHandlerBankAccountServices(List<BankAccountAdapter> bankAccountHandlers) {

        for (BankAccountAdapter bankAccountAdapter : bankAccountHandlers) {
            if (bankAccountAdapter.supports()) {
                return bankAccountAdapter;
            }
        }

        throw new IllegalArgumentException("handler getHandlerBankAccountServices 를 찾을 수 없습니다.");
    }


}
