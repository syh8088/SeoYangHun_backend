package com.wirebarley.domain.transfertransaction.repository;

import com.wirebarley.domain.transfertransaction.model.response.TransferTransactionOutPut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface TransferTransactionMapper {

	List<TransferTransactionOutPut> selectFirstTransferTransactionListByMemberNo(
			@Param("memberNo") long memberNo,
			@Param("limit") int limit

	);

	List<TransferTransactionOutPut> selectTransferTransactionListByMemberNo(
			@Param("memberNo") long memberNo,
			@Param("limit") int limit,
			@Param("transferTransactionNo") long transferTransactionNo,
			@Param("createdDateTime") LocalDateTime createdDateTime

	);
}
