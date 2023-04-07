package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CertificatePostRequest")
public class CertificatePostReq {

	private Long id;
	private String name; // 자격증, 어학점수명
	private String sortation; // 구분
	private String acquisitionDate; // 취득기간
	private String score; // 점수, 등급
	private Long userId;
}
