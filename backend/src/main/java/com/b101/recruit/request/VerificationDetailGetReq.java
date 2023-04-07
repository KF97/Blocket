package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("VerificationDetailGetRequest")
public class VerificationDetailGetReq {
	@ApiModelProperty(name = "검증 ID", example = "1")
	private Long id;

}
