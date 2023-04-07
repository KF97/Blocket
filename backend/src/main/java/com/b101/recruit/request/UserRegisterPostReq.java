package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {

	@ApiModelProperty(name = "유저 Email", example = "user_email@ssafy.com")
	private String email;
	@ApiModelProperty(name = "유저 Password", example = "user_password")
	private String password;
	@ApiModelProperty(name = "유저 Name", example = "user_name")
	private String name;
	@ApiModelProperty(name = "유저 Type", example = "0")
	private int type; // 0: 회원, 1: 기업
	@ApiModelProperty(name = "유저 Belong", example = "SSAFY")
	private String belong; // 소속
	@ApiModelProperty(name = "유저 PhoneNumber", example = "01012345678")
	private String phoneNumber; // 휴대폰번호
	@ApiModelProperty(name = "유저 Brn", example = "0123456789")
	private int brn; // 사업자등록 번호

}
