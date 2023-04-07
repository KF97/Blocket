package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ArmyUpdatePatchRequest")
public class MilitaryUpdatePatchReq {
	@ApiModelProperty(name = "병역 사항")
	private String militaryService;
}
