package com.b101.recruit.domain.repository;

import com.b101.recruit.domain.entity.QUserWallet;
import com.b101.recruit.domain.entity.User;
import com.b101.recruit.domain.entity.UserWallet;
import com.b101.recruit.request.UserWalletUpdatePatchReq;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class JpaUserWalletRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUserWallet qUserWallet = QUserWallet.userWallet;

    @Transactional
    public Optional<UserWallet> findByUserId(Long userId) {
        UserWallet userWallet = jpaQueryFactory.select(qUserWallet).from(qUserWallet).where(qUserWallet.user.id.eq(userId)).fetchOne();
        return Optional.ofNullable(userWallet);
    }

    @Transactional
    public long updateUserWallet(Long userId, UserWalletUpdatePatchReq userWalletUpdatePatchReq) {
        return jpaQueryFactory.update(qUserWallet).where(qUserWallet.user.id.eq(userId))
                .set(qUserWallet.address, userWalletUpdatePatchReq.getAddress())
                .set(qUserWallet.balance, userWalletUpdatePatchReq.getBalance())
                .set(qUserWallet.receiving_count, userWalletUpdatePatchReq.getReceiving_count())
                .execute();
    }

    @Transactional
    public long deleteUserWallet(User user) {
        return jpaQueryFactory.delete(qUserWallet).where(qUserWallet.user.id.eq(user.getId())).execute();
    }
}
