package com.b101.recruit.service;


import com.b101.recruit.domain.entity.Gallery;
import org.springframework.data.domain.Page;

import com.b101.recruit.domain.dto.GalleryDto;
import com.b101.recruit.domain.dto.VerificationDto;
import com.b101.recruit.domain.entity.Verification;
import com.b101.recruit.request.VerificationAcceptPatchReq;
import com.b101.recruit.request.VerificationListGetReq;
import com.b101.recruit.request.VerificationUpdatePatchReq;

public interface IVerificationService {

//	Certificate verifyCertificate(VerificationCetificatePatchReq verificationCetificatePutReq);
//	Activity verifyActivity(VerificationActivityPatchReq verificationActivityPutReq);
//	FinalEducation verifyFinalEducation(VerificationFinalEducationPatchReq verificationFinalEducationPatchReq);
//	Page<VerificationDto> getVerificationList(VerificationListGetReq verificationListGetReq);
//	Page<Verification> getVerifications(VerificationListGetReq verificationListGetReq);
//	Verification createVerification(VerificationCreatePostReq verificationCreatePostReq);
//	Verification getVerificationDetail(VerificationDetailGetReq verificationDetailGetReq);
//	Verification updateVerification(VerificationUpdatePatchReq verificationUpdatePatchReq);

//	void createVerification(FinalEducation fpr);
//	void createVerification(Activity apr);
//	void createVerification(Certificate cpr);
	
//	Verification createVerification(File file);

	Verification updateVerification(VerificationUpdatePatchReq vcpr);

	Page<VerificationDto> getVerificationList(VerificationListGetReq vlgr);

	Verification createVerification(Gallery gallery) throws NullPointerException;

	Verification getVerification(Long gId);

	void deleteByGallery(Gallery gallery);

	Verification acceptVerification(VerificationAcceptPatchReq vapr);

//	VerificationDto findByCertificationId(Long cId);

}
