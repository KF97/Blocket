package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("VerificationListGetRequest")
public class VerificationListGetReq {
	
	@ApiModelProperty(name = "페이지 Idx", example = "1")
	@ApiParam(value = "페이지 Idx", defaultValue = "1")
	private int page;

	@ApiModelProperty(name = "페이지 Size", example = "15")
	@ApiParam(value = "페이지 Size", defaultValue = "10")
	private int size;
	
	@ApiModelProperty(name = "검증 여부", example = "대기중")
	private String verified; // 입력값이 없을 경우 전체 조회
	

}
