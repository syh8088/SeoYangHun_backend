package com.wirebarley.domain.wallet.repository;

import com.wirebarley.domain.wallet.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}