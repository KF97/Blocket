package com.b101.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b101.common.model.response.BaseResponseBody;
import com.b101.recruit.auth.CustomUserDetails;
import com.b101.recruit.domain.dto.VerificationDto;
import com.b101.recruit.domain.entity.User;
import com.b101.recruit.domain.entity.Verification;
import com.b101.recruit.reponse.VerificationListRes;
import com.b101.recruit.reponse.VerificationRes;
import com.b101.recruit.reponse.VerificationUpdatePatchRes;
import com.b101.recruit.request.VerificationAcceptPatchReq;
import com.b101.recruit.request.VerificationDetailGetReq;
import com.b101.recruit.request.VerificationListGetReq;
import com.b101.recruit.request.VerificationUpdatePatchReq;
import com.b101.recruit.service.impl.UserService;
import com.b101.recruit.service.impl.VerificationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "검증 API", tags = { "Verification." })
@RestController
@RequestMapping("/api/recruit/verification")
public class VerificationController {

	@Autowired
	UserService userService;
	
	@Autowired
	VerificationService verificationService;
	
	@PatchMapping("/accept")
	@ApiOperation(value = "검증 승인 변경", notes = "검증 상태를 변경한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 400, message = "실패"),
		@ApiResponse(code = 401, message = "로그인 인증 실패"),@ApiResponse(code = 403, message = "잘못된 요청")})
	public ResponseEntity<? extends BaseResponseBody> verifyCertificate(
			@RequestBody @ApiParam(value = "검증 정보", required = true) VerificationAcceptPatchReq vapr,
			@ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "로그인 인증 실패"));
		} else {
			System.out.println("넘어온 값 확인");
			System.out.println(vapr.getGalleryId());
			System.out.println(vapr.getTHash());
			System.out.println("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");

			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
			String userEmail = userDetails.getUsername();

			User user = userService.findByUserEmail(userEmail);

			if (user != null &&user.getType()==2) {
				Verification verification = verificationService.acceptVerification(vapr);
				if(verification!=null)
					return ResponseEntity.ok(VerificationUpdatePatchRes.of(200, "변경이 완료되었습니다.", verification));
			}

			return ResponseEntity.status(403).body(BaseResponseBody.of(403, "잘못된 요청입니다."));
		}
	}
	
	// 검증 상태 변경
	@PatchMapping("")
	@ApiOperation(value = "검증 상태 변경", notes = "자격증 검증 상태를 변경한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 400, message = "실패"),
		@ApiResponse(code = 401, message = "로그인 인증 실패"),@ApiResponse(code = 403, message = "잘못된 요청")})
	public ResponseEntity<? extends BaseResponseBody> verifyCertificate(
			@RequestBody @ApiParam(value = "검증 정보", required = true) VerificationUpdatePatchReq vupr,
			@ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "로그인 인증 실패"));
		} else {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
			String userEmail = userDetails.getUsername();

			User user = userService.findByUserEmail(userEmail);

			if (user != null &&user.getType()==2) {
				Verification verification = verificationService.updateVerification(vupr);
				if(verification!=null)
					return ResponseEntity.ok(VerificationUpdatePatchRes.of(200, "검증이 완료되었습니다.", verification));
			}

			return ResponseEntity.status(403).body(BaseResponseBody.of(403, "잘못된 요청입니다."));
		}
	}

	
	// 검증 목록 조회
	@PostMapping("/list")
	@ApiOperation(value = "검증 목록 조회 조회", notes = "검증 목록을 조회한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 400, message = "실패"),
		@ApiResponse(code = 401, message = "로그인 인증 실패"),@ApiResponse(code = 403, message = "잘못된 요청")
	,@ApiResponse(code = 404, message = "검증 결과 없음")})
	public ResponseEntity<? extends BaseResponseBody> getVerificationList(
			@RequestBody @ApiParam(value = "검증 정보", required = true) VerificationListGetReq verificationListGetReq
			,@ApiIgnore Authentication authentication
			) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "로그인 인증 실패"));
		} else {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
			String userEmail = userDetails.getUsername();
			User user = userService.findByUserEmail(userEmail);

			if (user != null &&user.getType()==2) {
				Page<VerificationDto> verList = verificationService.getVerificationList(verificationListGetReq);
				if(!verList.isEmpty()) 
					return ResponseEntity.ok(VerificationListRes.of(200, "검증 목록 조회 완료.", verList));
				else return ResponseEntity.ok(VerificationListRes.of(404, "검증 결과 없음.", null));
			}

			return ResponseEntity.status(403).body(BaseResponseBody.of(403, "잘못된 요청입니다."));
		}
	}


//	// 자격증 ID로 검증 상세 조회
//	@GetMapping("/certificate/{cId}")
//	@ApiOperation(value = "검증 등록", notes = "검증 정보를 등록한다.")
//	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 400, message = "실패") })
//	public ResponseEntity<? extends BaseResponseBody> createConference(
//			@PathVariable("cId") @ApiParam(value = "검증 정보", required = true) Long cId,
//			@ApiIgnore Authentication authentication) {
//		if (authentication == null) {
//			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "로그인 인증 실패"));
//		} else {
//			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
//			String userEmail = userDetails.getUsername();
//
//			User user = userService.findByUserEmail(userEmail);
//
//			if (user != null) {
//				VerificationDto verification = verificationService.findByCertificationId(cId);
//				return ResponseEntity.ok(VerificationRes.of(200, "success", verification));
//			}
//
//		}
//		return null;
//	}


}
