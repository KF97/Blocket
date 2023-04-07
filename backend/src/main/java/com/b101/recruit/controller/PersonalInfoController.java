package com.b101.recruit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.b101.recruit.domain.entity.*;
import com.b101.recruit.service.impl.VerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.b101.common.model.response.BaseResponseBody;
import com.b101.recruit.auth.CustomUserDetails;
import com.b101.recruit.reponse.PersonalInfoPostRes;
import com.b101.recruit.request.ActivityPostReq;
import com.b101.recruit.request.MilitaryUpdatePatchReq;
import com.b101.recruit.request.CertificatePostReq;
import com.b101.recruit.request.DisabledUpdatePatchReq;
import com.b101.recruit.request.FinalEducationPostReq;
import com.b101.recruit.request.PersonalInfoPostReq;
import com.b101.recruit.service.impl.GalleryService;
import com.b101.recruit.service.impl.PersonalInfoService;
import com.b101.recruit.service.impl.S3Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "신상정보 API", tags = { "PersonalInfo." })
@RestController
@RequestMapping("/api/recruit/personalinfo")
public class PersonalInfoController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private PersonalInfoService service;

	@Autowired
	private GalleryService gService;

	@Autowired
	private VerificationService vService;
	
	// 저장할 기본 디렉토리 설정 application.property 파일에 설정하고 가져온다.
	@Value("${server.tomcat.basedir}")
	private String basedir;


	@PostMapping()
	@ApiOperation(value = "신상정보 등록", notes = "기본 신상정보를 등록한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<BaseResponseBody> createPersonalInfo(@RequestBody PersonalInfoPostReq personalinfoPostReq) {
		try {
			logger.debug("이메일 확인!! {}", personalinfoPostReq.getEmail());
			PersonalInfo personalinfo = service.createPersonalInfo(personalinfoPostReq);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}


	@GetMapping("/{personalinfoId}")
	@ApiOperation(value = "신상정보 상세내용", notes = "신상정보의 상세 내용을 확인한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<PersonalInfoPostRes> getonePersonalInfo(@PathVariable(name = "personalinfoId") Long id, @ApiIgnore Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getDetails();
		String email = userDetails.getName();
		PersonalInfoPostRes personalinfo = service.getonePersonalInfo(id, email);
		return ResponseEntity.status(200).body(personalinfo);
	}


	@GetMapping("/pic")
	@ApiOperation(value = "신상정보에 등록된 사진 정보")
	public void housepic(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
			@RequestParam("sfile") String sfile, HttpServletResponse response) {
		String origin = basedir + File.separator + sfolder + File.separator + sfile;
		try {
			File down = new File(origin);
			FileInputStream is = new FileInputStream(down);// 서버에 저장된 파일 읽어서

			response.setContentType("application/octet-stream"); // 모든 content-type을 구성할 수 있는 설정
			// 헤더 설정 전송 인코딩
			response.setHeader("Content-Disposition", "attachment; filename=\"" + ofile + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			// 이름을 통해 콘텐트 타입을 가져온다.
			String mimeType = URLConnection.guessContentTypeFromName(origin);
			response.setContentType(mimeType);
			// 콘텐츠 길이를 설정
			response.setContentLengthLong(down.length());
			// 데이터 전송
			ServletOutputStream os = response.getOutputStream();
			FileCopyUtils.copy(is, os);
			os.flush();
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@PutMapping("/{personalinfoId}/updatePersonalInfo")
	@ApiOperation(value = "신상정보 수정", notes = "신상정보를 수정한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<PersonalInfo> updatePersonalInfo(@PathVariable(name = "personalinfoId") Long id,
			@RequestBody PersonalInfoPostReq personalinfoPostReq) {
		PersonalInfo result = service.updatePersonalInfo(id, personalinfoPostReq);
		return ResponseEntity.status(200).body(result);
	}


	@DeleteMapping("/{personalinfoId}")
	@ApiOperation(value = "신상정보 삭제", notes = "신상정보를 삭제한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<BaseResponseBody> deletePersonalInfo(@PathVariable(name = "personalinfoId") Long id) {
		service.deletePersonalInfo(id);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}


	@GetMapping("/allCount")
	@ApiOperation(value = "신상정보 전체 카운트", notes = "페이지 구성")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Long> getAllPersonalInfoCount() {
		long size = service.getAllPersonalInfoCount();
		return ResponseEntity.status(200).body(size);
	}


	@GetMapping("/all")
	@ApiOperation(value = "신상정보 전체 목록", notes = "페이지랑 한페이지에 들어갈 사이즈 >> ?size=10&page=1")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<List<PersonalInfoPostReq>> getAllPersonalInfo(Pageable pageable, @ApiIgnore Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getDetails();
		String email = userDetails.getName();
		List<PersonalInfoPostReq> personalinfoList = service.getAllPersonalInfo(pageable, email);
		return ResponseEntity.status(200).body(personalinfoList);
	}


	@GetMapping("/{personalinfoId}/myCertificate")
	@ApiOperation(value = "어학, 자격증 조회", notes = "어학, 자격증을 조회한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Optional<List<Certificate>>> getCertification(@PathVariable("personalinfoId") Long id,
		@ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			Optional<List<Certificate>> result = service.getCertification(id);
			return ResponseEntity.status(200).body(result);
		}
	}


	@PostMapping("/{personalinfoId}/certificate")
	@ApiOperation(value = "어학, 자격증 등록", notes = "어학, 자격증을 등록한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Optional<List<Certificate>>> createCertificate(@PathVariable(name = "personalinfoId") Long id,
			@RequestBody CertificatePostReq certificate, @ApiIgnore Authentication authentication) {

		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			Certificate certificate2 = service.createCertificate(id, certificate);
			return getCertification(id, authentication);
		}
	}
	
//	@PutMapping("/{personalinfoId}/{certificateId}")
//	@ApiOperation(value = "어학, 자격증 수정", notes = "어학, 자격증을 수정한다.")
//	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
//		@ApiResponse(code = 500, message = "서버 오류") })
//	public ResponseEntity<Certificate> updateCertificate(@PathVariable(name = "personalinfoId") Long pId,
//			@PathVariable(name = "certificateId") Long cId, @RequestBody CertificatePostReq certificate) {
//		Certificate certificate2 = service.updateCertificate(pId, cId, certificate);
//		return ResponseEntity.status(200).body(certificate2);
//	}

	@Transactional
	@DeleteMapping("/{personalinfoId}/{certificateId}/CertDelete")
	@ApiOperation(value = "어학, 자격증 삭제", notes = "어학, 자격증을 삭제한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Optional<List<Certificate>>> deleteCertificate(@PathVariable(name = "personalinfoId") Long pId,
			@PathVariable(name = "certificateId") Long cId, @ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			// 어학, 자격증 삭제하기 전에 Verifaction, gallery 삭제하기
			Gallery result = gService.findByPidAndSidAndSortation(pId, cId, "cert");
			// Verification 삭제
			vService.deleteByGallery(result);
			// 갤러리 파일 삭제
			gService.deleteGallery(result.getId());
			// 어학, 자격증 삭제
			service.deleteCertificate(pId, cId);
			return getCertification(pId, authentication);
		}
	}


	@GetMapping("/{personalinfoId}/myActivity")
	@ApiOperation(value = "활동사항 조회", notes = "활동사항을 조회한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Optional<List<Activity>>> getActivities(@PathVariable("personalinfoId") Long id,
			@ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			Optional<List<Activity>> result = service.getActivities(id);
			return ResponseEntity.status(200).body(result);
		}
	}


	@PostMapping("/{personalinfoId}/activity")
	@ApiOperation(value = "활동사항 등록", notes = "활동사항을 등록한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Optional<List<Activity>>> createActivity(@PathVariable(name = "personalinfoId") Long id,
			@RequestBody ActivityPostReq activity, @ApiIgnore Authentication authentication) {

		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			Activity activity2 = service.createActivity(id, activity);
			// 등록한 후, 모든 목록을 불러온다.
			return getActivities(id, authentication);
		}
	}


//	@PutMapping("/{personalinfoId}/{activityId}/update")
//	@ApiOperation(value = "활동사항 수정", notes = "활동사항을 수정한다.")
//	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
//		@ApiResponse(code = 500, message = "서버 오류") })
//	public ResponseEntity<Activity> updateActivity(@PathVariable(name = "personalinfoId") Long pId,
//			@PathVariable(name = "activityId") Long aId, @RequestBody ActivityPostReq activity) {
//		Activity activity2 = service.updateActivity(pId, aId, activity);
//		return ResponseEntity.status(200).body(activity2);
//	}


	@Transactional
	@DeleteMapping("/{personalinfoId}/{activityId}/ActDelete")
	@ApiOperation(value = "활동사항 삭제", notes = "활동사항을 삭제한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Optional<List<Activity>>> deleteActivity(@PathVariable(name = "personalinfoId") Long pId,
			@PathVariable(name = "activityId") Long aId, @ApiIgnore Authentication authentication) {
		if ( authentication == null ) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			// 1. gallery 객체 먼저 찾기
			Gallery result = gService.findByPidAndSidAndSortation(pId, aId, "act");

			// Verification 삭제
			vService.deleteByGallery(result);
			// 갤러리 파일 삭제
			gService.deleteGallery(result.getId());
			// 활동사항 삭제
			service.deleteActivity(pId, aId);
			// 목록 반환.
			return getActivities(pId, authentication);
		}
	}


	@GetMapping("{personalInfoId}/myFinalEducation")
	@ApiOperation(value = "최종학력 조회", notes = "최종학력을 조회한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Optional<List<FinalEducation>>> getFinalEducation(@PathVariable("personalInfoId") long personalInfoId) {


		Optional<List<FinalEducation>> result = service.getFinalEducation(personalInfoId);
		return ResponseEntity.status(200).body(result);
	}


	@PostMapping("/{personalinfoId}/finaleducation")
	@ApiOperation(value = "최종학력 등록", notes = "최종학력을 등록한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<FinalEducation> createFinalEducation(@RequestBody FinalEducationPostReq FinalEducationPostReq, @PathVariable(name = "personalinfoId") Long id
			) {

		logger.info("최종 학력 등록 메서드");
		logger.info("PersonalInfoId: {}", id);

		FinalEducation result = service.createFinalEducation(id, FinalEducationPostReq);
		return ResponseEntity.status(200).body(result);
	}

	@PutMapping("/{personalinfoId}/{finaleducationId}/update")
	@ApiOperation(value = "최종학력 수정", notes = "최종학력을 수정한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
		@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<FinalEducation> updateFinalEducation(@PathVariable(name = "personalinfoId") Long pId,
			@PathVariable(name = "finaleducationId") Long fId, @RequestBody FinalEducationPostReq finaleducation,
		   @ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			FinalEducation finaleducation2 = service.updateFinalEducation(pId, fId, finaleducation);
			return ResponseEntity.status(200).body(finaleducation2);
		}
	}
	
//	@DeleteMapping("/{personalinfoId}/{finaleducationId}/delete")
//	@ApiOperation(value = "최종학력 삭제", notes = "최종학력을 삭제한다.")
//	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
//		@ApiResponse(code = 500, message = "서버 오류") })
//	public ResponseEntity<BaseResponseBody> deleteFinalEducation(@PathVariable(name = "personalinfoId") Long pId,
//			@PathVariable(name = "finaleducationId") Long fId) {
//		service.deleteFinalEducation(pId, fId);
//		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
//	}


	// 활동사항에 대한 검증 내용 불러오기
	@PostMapping("/{personalInfoId}/{activityId}/findActVerif")
	@ApiOperation(value = "활동사항에 대한 검증 내용 불러오기", notes = "활동 사항에 대한 검증 내역을 불러온다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Verification> findActVerif(@PathVariable("personalInfoId") long pid,
													 @PathVariable("activityId") long aid, @ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			Gallery gallery = gService.findByPidAndSidAndSortation(pid, aid, "act");
			// 갤러리 통해서 검증 내역 확인할 수 있다.
			Verification result = vService.getVerification(gallery.getId());
			return ResponseEntity.status(200).body(result);
		}
	}

	// 어학, 자격증에 대한 검증 내용 불러오기
	@PostMapping("/{personalInfoId}/{certId}/findCertVerif")
	@ApiOperation(value = "어학, 자격증에 대한 검증 내용 불러오기", notes = "어학, 자격증에 대한 검증 내역을 불러온다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Verification> findCertVerif(@PathVariable("personalInfoId") long pid,
													 @PathVariable("certId") long cid, @ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			Gallery gallery = gService.findByPidAndSidAndSortation(pid, cid, "cert");
			// 갤러리 통해서 검증 내역 확인할 수 있다.
			Verification result = vService.getVerification(gallery.getId());
			return ResponseEntity.status(200).body(result);
		}
	}


	// 최종학력에 대한 검증 내용 불러오기
	@PostMapping("/{personalInfoId}/{eduId}/findEduVerif")
	@ApiOperation(value = "최종학력에 대한 검증 내용 불러오기", notes = "최종학력에 대한 검증 내역을 불러온다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<Verification> findEduVerif(@PathVariable("personalInfoId") long pid,
													  @PathVariable("eduId") long eid, @ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			Gallery gallery = gService.findByPidAndSidAndSortation(pid, eid, "edu");
			// 갤러리 통해서 검증 내역 확인할 수 있다.
			Verification result = vService.getVerification(gallery.getId());
			return ResponseEntity.status(200).body(result);
		}
	}
	
	// 병역사항 변경
	@PatchMapping("/{personalInfoId}/military")
	@ApiOperation(value = "보훈 정보 불러오기", notes = "보훈 정보를 불러온다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<PersonalInfo> updateMilitary(@PathVariable("personalInfoId") long pId,
			@RequestBody @ApiParam(value = "검증 정보", required = true) MilitaryUpdatePatchReq mupr, @ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			PersonalInfo personalInfo = service.updateMilitary(pId,mupr);
			if(personalInfo!=null)
				return ResponseEntity.status(200).body(personalInfo);
			return ResponseEntity.status(404).body(null);
		}
	}

	// 병역사항 변경
	@PatchMapping("/{personalInfoId}/disabled")
	@ApiOperation(value = "보훈 정보 불러오기", notes = "보훈 정보를 불러온다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<PersonalInfo> updateMilitary(@PathVariable("personalInfoId") long pId,
			@RequestBody @ApiParam(value = "검증 정보", required = true) DisabledUpdatePatchReq dupr, @ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		else {
			PersonalInfo personalInfo = service.updateDisabled(pId, dupr);
			if(personalInfo!=null)
				return ResponseEntity.status(200).body(personalInfo);
			return ResponseEntity.status(404).body(null);
		}
	}




	
}
