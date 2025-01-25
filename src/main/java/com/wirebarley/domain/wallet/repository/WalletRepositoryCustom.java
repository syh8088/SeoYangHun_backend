package com.wirebarley.domain.wallet.repository;

import com.wirebarley.domain.wallet.model.response.WalletOutPut;
import com.wirebarley.domain.wallet.model.response.WalletTransactionOutPut;
import com.wirebarley.domain.wallet.model.response.WalletWithMemberOutPut;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WalletRepositoryCustom {


    Optional<WalletOutPut> selectWalletByMemberNo(long memberNo);

    Optional<WalletWithMemberOutPut> selectWalletWithMemberByMemberNo(long memberNo);

    Optional<BigDecimal> selectBalanceByMemberNo(long memberNo);

    List<WalletTransactionOutPut> selectWalletTransactionList(long walletNo);
}
