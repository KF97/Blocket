package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ActivityPostRequest")
public class ActivityPostReq {

	private Long id;
	private String name;		// 이름
	private String activity;	//활동 구분
	private String period;		//기간
	private String description;	// 설명
	
	private Long userId;
	
}
