package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PasswordUpdatePatchRequest")
public class PasswordUpdatePatchReq {
	@ApiModelProperty(name = "현재 Password", example = "your_password")
	private String password;
	@ApiModelProperty(name = "새로운 Password", example = "new_password")
	private String newPassword;
}
