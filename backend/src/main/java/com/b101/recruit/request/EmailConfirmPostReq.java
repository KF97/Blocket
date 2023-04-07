package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EmailConfirmPostRequest")
public class EmailConfirmPostReq {
	@ApiModelProperty(name = "유저 Email", example = "user_email@ssafy.com")
	String email;
}
