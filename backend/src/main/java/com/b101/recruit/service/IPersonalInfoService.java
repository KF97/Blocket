package com.b101.recruit.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.b101.recruit.domain.dto.GalleryDto;
import com.b101.recruit.domain.entity.Activity;
import com.b101.recruit.domain.entity.Certificate;
import com.b101.recruit.domain.entity.FinalEducation;
import com.b101.recruit.domain.entity.PersonalInfo;
import com.b101.recruit.reponse.PersonalInfoPostRes;
import com.b101.recruit.request.ActivityPostReq;
import com.b101.recruit.request.CertificatePostReq;
import com.b101.recruit.request.FinalEducationPostReq;
import com.b101.recruit.request.MilitaryUpdatePatchReq;
import com.b101.recruit.request.PersonalInfoPostReq;

public interface IPersonalInfoService {

	PersonalInfo createPersonalInfo(PersonalInfoPostReq personalinfoPostReq) throws IllegalStateException, IOException;
	
	PersonalInfoPostRes getonePersonalInfo(Long id, String email);
	
	PersonalInfo updatePersonalInfo(Long id, PersonalInfoPostReq personalinfoPostReq);
	
	void deletePersonalInfo(Long id);
	
	long getAllPersonalInfoCount();
	
	List<PersonalInfoPostReq> getAllPersonalInfo(Pageable pageable, String email);

	Optional<List<Certificate>> getCertification(Long id);

	Certificate createCertificate(Long id, CertificatePostReq certificate);
	
//	Certificate updateCertificate(Long pId, Long cId, CertificatePostReq certificate);
	
	void deleteCertificate(Long pId, Long cId);

	Optional<List<Activity>> getActivities(Long id);

	Activity createActivity(Long id, ActivityPostReq activity);
	
//	Activity updateActivity(Long pId, Long aId, ActivityPostReq activity);
	
	void deleteActivity(Long pId, Long aId);

	Optional<List<FinalEducation>> getFinalEducation(Long personalInfoId);

	FinalEducation createFinalEducation(Long id, FinalEducationPostReq finaleducation);
	
	FinalEducation updateFinalEducation(Long pId, Long fId, FinalEducationPostReq finaleducation);
	
	void deleteFinalEducation(Long pId, Long fId);

	Object getSortationDetail(String sortation, Long sId);

	PersonalInfo updateMilitary(long pId, MilitaryUpdatePatchReq mupr);
	
}
