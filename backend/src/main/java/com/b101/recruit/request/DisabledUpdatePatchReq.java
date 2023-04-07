package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("DisabledUpdatePatchRequest")
public class DisabledUpdatePatchReq {

	@ApiModelProperty(name = "장애 여부")
	private String disabled;
}
