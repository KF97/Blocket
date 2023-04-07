package com.b101.recruit.domain.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.QUser;
import com.b101.recruit.request.UserUpdatePatchReq;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class JpaUserRepository {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QUser qUser = QUser.user;
	
	@Transactional
	public long deleteUser(String userEmail) {
		long user = jpaQueryFactory.delete(qUser).where(qUser.email.eq(userEmail)).execute();
		return user;
	}

	@Transactional
	public long updateUser(String userEmail, UserUpdatePatchReq UserUpdatePatchReq) {
		long user = jpaQueryFactory.update(qUser).where(qUser.email.eq(userEmail))
				.set(qUser.belong, UserUpdatePatchReq.getBelong()).set(qUser.brn,UserUpdatePatchReq.getBrn())
				.set(qUser.name, UserUpdatePatchReq.getName()).set(qUser.type, UserUpdatePatchReq.getType())
				.set(qUser.phoneNumber, UserUpdatePatchReq.getPhoneNumber())
				.execute();
		return user;
	}
	
	@Transactional
	public long updatePassword(String userEmail, String password) {
		long user = jpaQueryFactory.update(qUser).where(qUser.email.eq(userEmail)).set(qUser.password, password)
				.execute();
		return user;
	}
}
