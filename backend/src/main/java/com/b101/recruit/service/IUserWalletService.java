package com.b101.recruit.service;

import com.b101.recruit.domain.entity.UserWallet;
import com.b101.recruit.request.UserWalletRegisterPostReq;
import com.b101.recruit.request.UserWalletUpdatePatchReq;

import java.util.Optional;

public interface IUserWalletService {
    void createUserWallet(UserWalletRegisterPostReq userWalletRegisterPostReq);
    Optional<UserWallet> findUserWallet(Long userId);
    long updateUserWallet(Long userId, UserWalletUpdatePatchReq userWalletUpdatePatchReq);
}
