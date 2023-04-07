package com.b101.recruit.controller;

import javax.mail.MessagingException;

import com.b101.recruit.domain.entity.PersonalInfo;
import com.b101.recruit.reponse.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.b101.common.model.response.BaseResponseBody;
import com.b101.common.util.JwtTokenUtil;
import com.b101.recruit.auth.CustomUserDetails;
import com.b101.recruit.domain.entity.User;
import com.b101.recruit.request.EmailConfirmPostReq;
import com.b101.recruit.request.PasswordUpdatePatchReq;
import com.b101.recruit.request.UserLoginPostReq;
import com.b101.recruit.request.UserRegisterPostReq;
import com.b101.recruit.request.UserUpdatePatchReq;
import com.b101.recruit.service.impl.PasswordFindPatchReq;
import com.b101.recruit.service.impl.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "유저 API", tags = { "User." })
@RestController
@RequestMapping("/api/recruit/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	// 회원 가입
	@PostMapping("/register")
	@ApiOperation(value = "회원가입", notes = "사용자의 정보를 입력하여 회원가입한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 401, message = "fail"),
			@ApiResponse(code = 404, message = "url error"), @ApiResponse(code = 500, message = "server error") })
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody UserRegisterPostReq userRegisterPostReq) {
		try {
			userService.createUser(userRegisterPostReq);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "회원가입에 성공하였습니다."));
		} catch (Exception e) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "회원가입에 실패하였습니다."));
		}
	}
	
	// 로그인
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "로그인 성공", response = UserLoginPostRes.class) })
	public ResponseEntity<UserLoginPostRes> login(
			@RequestBody @ApiParam(value = "로그인 정보", required = true) UserLoginPostReq userLoginPostReq) {
		String userEmail = userLoginPostReq.getEmail();
		String password = userLoginPostReq.getPassword();
		User user = userService.findByUserEmail(userEmail);
		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				return ResponseEntity.ok(UserLoginPostRes.of(200, "로그인에 성공하였습니다.", JwtTokenUtil.createToken(user.getEmail()),user.getType()));
			}
		}
		return ResponseEntity.ok(UserLoginPostRes.of(404, "아이디 또는 비밀번호가 일치하지 않습니다.", null, -1));
	}
	
	// 회원 가입에 필요한 아이디 중복체크
	@GetMapping("/{userEmail}")
	@ApiOperation(value = "아이디 중복체크", notes = "존재하는 아이디인지 아닌지 확인한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원가입 가능한 아이디입니다."),
			@ApiResponse(code = 404, message = "url error"), @ApiResponse(code = 409, message = "이미 존재하는 사용자 아이디입니다."),
			@ApiResponse(code = 500, message = "server error") })
	public ResponseEntity<? extends BaseResponseBody> confirmUserId(@PathVariable("userEmail") String userEmail) {
		if (!userService.confirmUserEmail(userEmail)) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "회원가입 가능한 아이디입니다."));
		}
		return ResponseEntity.ok(BaseResponseBody.of(409, "이미 존재하는 사용자 아이디입니다."));
	}
	
	// 로그인한 회원의 정보 조회
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"), @ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<? extends BaseResponseBody> getUserInfo(@ApiIgnore Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "로그인 인증 실패"));
		} else {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
			String userEmail = userDetails.getUserEmail();
			User user = userService.findByUserEmail(userEmail);
			if (user != null)
				return ResponseEntity.ok(UserRes.of(200, user));
		}
		return ResponseEntity.status(404).body(BaseResponseBody.of(404, "존재하지 않는 회원입니다."));
	}
	
	// 로그인된 회원의 회원 탈퇴
	@DeleteMapping("/me")
	@ApiOperation(value = "회원 정보 삭제", notes = "회원 정보 삭제")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "탈퇴 실패"), @ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<? extends BaseResponseBody> deleteUser(@ApiIgnore Authentication authentication) {
		System.out.println("로그인한 회원의 탈퇴 로직 시작");
		System.out.println("======");
		System.out.println(authentication);
		if (authentication == null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "로그인 인증 실패"));
		} else {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
			String userEmail = userDetails.getUserEmail();
			long user = userService.deleteUser(userEmail);
			if (user > 0) {
				return ResponseEntity.ok(BaseResponseBody.of(200, "탈퇴되었습니다."));
			} else {
				return ResponseEntity.status(404).body(BaseResponseBody.of(404, "탈퇴 실패하였습니다."));
			}
		}
	}
	
	// 로그인된 회원의 회원정보 수정
	@PatchMapping("/me")
	@ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "수정 실패"), @ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<UserUpdatePatchRes> patchUser(@ApiIgnore Authentication authentication,
			@RequestBody @ApiParam(value = "수정 정보") UserUpdatePatchReq UserUpdatePatchReq) {
		System.out.println("회원수정");
		System.out.println(authentication);
		if (authentication == null) {
			return ResponseEntity.status(401).body(UserUpdatePatchRes.of(401, "로그인 인증 실패"));
		} else {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
			String userEmail = userDetails.getUserEmail();
			long user = userService.updateUser(userEmail, UserUpdatePatchReq);
			if (user > 0) {
				return ResponseEntity.ok(UserUpdatePatchRes.of(200, "수정이 완료됐습니다."));
			} else {
				return ResponseEntity.status(404).body(UserUpdatePatchRes.of(404, "수정 실패하였습니다."));
			}
		}
	}
	
	// 로그인된 회원의 비밀번호 수정
	@PatchMapping("/me/password")
	@ApiOperation(value = "회원 비밀번호 수정", notes = "회원 비밀번호 수정")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "수정 실패"), @ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<BaseResponseBody> patchPassword(@ApiIgnore Authentication authentication,
			@RequestBody @ApiParam(value = "수정 정보") PasswordUpdatePatchReq passwordUpdatePatchReq) {

		if (authentication == null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "로그인 인증 실패"));
		} else {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
			String userEmail = userDetails.getUsername();

			User user = userService.findByUserEmail(userEmail);

			if (user != null) {
				if (passwordEncoder.matches(passwordUpdatePatchReq.getPassword(), user.getPassword())) {
					long result = userService.updatePassword(user.getEmail(), passwordUpdatePatchReq.getNewPassword());
					if(result > 0)
						return ResponseEntity.ok(BaseResponseBody.of(200, "수정이 완료됐습니다."));
				}
			}
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "수정 실패했습니다."));
		}
	}
	
		
		
	// 이메일 확인
	@PostMapping("/emailConfirm")
	@ApiOperation(value = "이메일 인증", notes = "이메일 인증")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "수정 실패"), @ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<BaseResponseBody> AuthEmail(
			@RequestBody @ApiParam(value = "이메일 정보") EmailConfirmPostReq emailConfirmPostReq) {
		String emailToken = userService.AuthEmail(emailConfirmPostReq);
		if (emailToken != null) {
			return ResponseEntity.status(200).body(EmailConfirmPostRes.of(200, "메일이 발송되었습니다!", emailToken));
		} else {
			return ResponseEntity.status(404).body(EmailConfirmPostRes.of(404, "메일 발송에 실패하였습니다!", null));
		}
	}
		
	// 로그인된 회원의 비밀번호 찾기
	@PatchMapping("/passwordFind")
	@ApiOperation(value = "회원 비밀번호 찾기", notes = "회원 비밀번호 찾기")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "회원 정보 불일치"),
			@ApiResponse(code = 405, message = "메일 전송 실패"), @ApiResponse(code = 409, message = "존재하지 않는 회원 ID"),
			@ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<BaseResponseBody> findPassword(
			@RequestBody @ApiParam(value = "유저 정보") PasswordFindPatchReq passwordFindPatchReq) {
		long user;
		try {
			user = userService.findPassword(passwordFindPatchReq);
			if (user == 1) {
				return ResponseEntity.status(401).body(BaseResponseBody.of(401, "회원 정보가 일치하지 않습니다!"));
			} else if (user == 0){
				return ResponseEntity.status(409).body(BaseResponseBody.of(409, "존재하지 않는 회원 ID 입니다!"));
			} else{
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "메일이 발송되었습니다!"));
			}
		} catch (MessagingException e) {
			return ResponseEntity.status(405).body(BaseResponseBody.of(405, "메일 발송 중 오류가 발생하였습니다!"));
		}
	}

	// 사용자의 신상정보 ID를 불러온다.
	@GetMapping("/getMyInfo")
	@ApiOperation(value = "회원 신상정보 ID 찾기", notes = "사용자 이메일 통해 신상정보 조회하기")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "토큰 인증 실패"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), @ApiResponse(code = 409, message = "존재하지 않는 회원 ID")})
	public ResponseEntity<List<PersonalInfo>> findInfoId(@RequestParam("userEmail") String userEmail, @ApiIgnore Authentication authentication) {
		// No Convertor Error 발생,, PersonalInfo 엔티티 사용

		// 권한이 없다면
		if (authentication == null) {
			return ResponseEntity.status(401).body(null);
		}
		// 권한 유효
		else {
			// 먼저 사용자의 이메일을 통해 PK를 찾아야 한다.
			User user = userService.findByUserEmail(userEmail);

			// 찾은 유저의 PK를 이용하여 personalInfo 값을 찾는다.
			List<PersonalInfo> infoId = userService.getMyInfo(user.getId());
			return ResponseEntity.status(200).body(infoId);
		}

	}

}
