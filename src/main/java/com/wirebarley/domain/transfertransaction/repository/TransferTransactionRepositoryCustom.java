package com.wirebarley.domain.transfertransaction.repository;



import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;

import java.util.List;

public interface TransferTransactionRepositoryCustom {


    List<TransferTransactionOutPut> selectFirstTransferTransactionListByMemberNo(long memberNo);
}
