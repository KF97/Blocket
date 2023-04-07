package com.b101.recruit.service;

import javax.mail.MessagingException;

import com.b101.recruit.domain.entity.FinalEducation;
import com.b101.recruit.domain.entity.PersonalInfo;
import com.b101.recruit.domain.entity.User;
import com.b101.recruit.request.EmailConfirmPostReq;
import com.b101.recruit.request.UserRegisterPostReq;
import com.b101.recruit.request.UserUpdatePatchReq;
import com.b101.recruit.service.impl.PasswordFindPatchReq;

import java.util.List;

public interface IUserSerive {
	
	User findByUserEmail(String userEmail);
	void createUser(UserRegisterPostReq registerInfo);
	long updatePassword(String userEmail, String newPassword);
	long deleteUser(String userEmail);
	long updateUser(String userEmail, UserUpdatePatchReq UserUpdatePatchReq);
	boolean confirmUserEmail(String userEmail);
	long findPassword(PasswordFindPatchReq passwordFindPatchReq) throws MessagingException;
	String AuthEmail(EmailConfirmPostReq emailConfirmPostReq);
	List<PersonalInfo> getMyInfo(long user);
}
