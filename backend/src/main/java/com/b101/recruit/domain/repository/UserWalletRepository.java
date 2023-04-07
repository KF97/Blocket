package com.b101.recruit.domain.repository;

import com.b101.recruit.domain.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
}
