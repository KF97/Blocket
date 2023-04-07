package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserLoginPostRequest")
public class UserLoginPostReq {
	@ApiModelProperty(name = "유저 Email", example = "your_email@ssafy.com")
	private String email;
	@ApiModelProperty(name = "유저 Password", example = "your_password")
	private String password;
}
