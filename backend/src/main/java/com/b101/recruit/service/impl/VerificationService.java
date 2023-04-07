package com.b101.recruit.service.impl;

import java.util.Optional;

import com.b101.recruit.domain.dto.GalleryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.b101.recruit.domain.dto.VerificationDto;
import com.b101.recruit.domain.entity.Gallery;
import com.b101.recruit.domain.entity.PersonalInfo;
import com.b101.recruit.domain.entity.Verification;
import com.b101.recruit.domain.repository.PersonalInfoRepository;
import com.b101.recruit.domain.repository.VerificationRepository;
import com.b101.recruit.request.VerificationAcceptPatchReq;
import com.b101.recruit.request.VerificationListGetReq;
import com.b101.recruit.request.VerificationUpdatePatchReq;
import com.b101.recruit.service.IVerificationService;

@Service("verificationService")
public class VerificationService implements IVerificationService {

	@Autowired
	VerificationRepository verificationRepository;

	@Autowired
	PersonalInfoRepository personalInfoRepository;

	@Override
	public Verification createVerification(Gallery gallery) throws NullPointerException {

		Verification verification = new Verification();

		PersonalInfo personalInfo = personalInfoRepository.findOneById(gallery.getPid());
		Long userId = personalInfo.getUser().getId();

		verification.setPersonalinfo(personalInfo);
		verification.setCurrentStatus("승인대기");
		verification.setGallery(gallery);
		verification.setUserId(userId);

		return verificationRepository.save(verification);
	}

	@Override
	public Verification updateVerification(VerificationUpdatePatchReq vcpr) {
		Optional<Verification> verification = verificationRepository.findByGalleryId(vcpr.getFileId());
		if (verification.isPresent()) {
			String status = vcpr.getVerified();
			verification.get().setCurrentStatus(status);
			verification.get().setReasonsRejection(vcpr.getReasonsRejection());
			return verificationRepository.save(verification.get());
		}
		return null;
	}

	@Override
	public Page<VerificationDto> getVerificationList(VerificationListGetReq vlgr) {
		int size = vlgr.getSize();
		int page = vlgr.getPage() <= 0 ? 0 : vlgr.getPage() - 1;
		String verified = vlgr.getVerified();
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registrationDate"));
		if (verified.equals("")) {
			
			Page<Verification> pageTuts = verificationRepository.findByGallery_SortationNotLike(pageable,"prop");
//			Page<Verification> pageTuts = verificationRepository.findAll(pageable);
			Page<VerificationDto> verList = pageTuts.map(m -> new VerificationDto(m.getId(),m.getPersonalinfo().getId(),m.getUserId(),m.getGallery().getId(),m.getRegistrationDate(),m.getCurrentStatus(),m.getReasonsRejection()));
			
//			Page<VerificationDto> verList = new PageImpl<>()
			return verList;
		} else {
			Page<Verification> pageTuts = verificationRepository.findByCurrentStatusContainingAndGallery_SortationNotLike(pageable, verified,"prop");
			Page<VerificationDto> verList = pageTuts.map(m -> new VerificationDto(m.getId(),m.getPersonalinfo().getId(),m.getUserId(),m.getGallery().getId(),m.getRegistrationDate(),m.getCurrentStatus(),m.getReasonsRejection()));
			return verList;
		}
	}

	@Override
	public Verification getVerification(Long gId) {
		return verificationRepository.findByGalleryId(gId).get();
	}
	
//	@Override
//	public VerificationDto findByCertificationId(Long cId) {
//		
//		Verification v = verificationRepository.findBySid(cId);
//		VerificationDto verificationDto = new VerificationDto(v.getId(),v.getPersonalinfo().getId(),v.getUserId(),v.getGallery().getId(),v.getRegistrationDate(),v.getCurrentStatus(),v.getReasonsRejection());
//		return null;
//	}
//	
//	@Override
//	public VerificationDto findByActivityId(Long aId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	@Override
//	public VerificationDto findByFinalEducationId(Long fId) {
//		// TODO Auto-generated method stub
//		return null;
//	}


	@Override
	public void deleteByGallery(Gallery gallery) {
		verificationRepository.deleteByGallery(gallery);
	}

	@Override
	public Verification acceptVerification(VerificationAcceptPatchReq vapr) {
		Optional<Verification> verification = verificationRepository.findByGalleryId(vapr.getGalleryId());
		if(verification.isPresent()) {
			String tHash = vapr.getTHash();
			verification.get().setReasonsRejection(tHash);
			return verificationRepository.save(verification.get());
		}
		return null;
	}
}

