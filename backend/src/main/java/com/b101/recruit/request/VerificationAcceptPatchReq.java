package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("VerificationAcceptPatchRequest")
public class VerificationAcceptPatchReq {

	@ApiModelProperty(name = "파일 ID")
	private Long galleryId;
	@ApiModelProperty(name = "트랜잭션 해쉬값")
	private String tHash;
}
