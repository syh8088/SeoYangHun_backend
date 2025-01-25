package com.wirebarley.domain.wallet.service;

import com.wirebarley.domain.member.model.entity.Member;
import com.wirebarley.domain.wallet.model.entity.Wallet;
import com.wirebarley.domain.wallet.repository.WalletRepository;
import com.wirebarley.global.util.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public void saveWallet(Member member) {

        Wallet wallet = Wallet.of(snowflake.nextId(), member);
        walletRepository.save(wallet);
    }


}
