package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;

import lombok.Data;
import lombok.Setter;

import lombok.Getter;

@Data
@ApiModel("FinalEducationPostRequest")
public class FinalEducationPostReq {
	private Long id;
	private String name; // 최종 학교 이름
	private String sortation; // 학교 구분
	private String grades; // 성적
	private Long userId;

}
