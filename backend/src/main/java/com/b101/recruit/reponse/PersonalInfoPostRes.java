package com.b101.recruit.reponse;

import java.sql.Date;
import java.util.List;

import com.b101.recruit.domain.entity.Certificate;
import com.b101.recruit.request.ActivityPostReq;
import com.b101.recruit.request.CertificatePostReq;
import com.b101.recruit.request.FinalEducationPostReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PersonalInfoPostResponse")
public class PersonalInfoPostRes {

	@ApiModelProperty(name = "신상정보 id", example = "Long")
	private Long id;
	@ApiModelProperty(name = "유저 Email", example = "your_email@ssafy.com")
	private String email;
	@ApiModelProperty(name = "유저 이름")
	private String name;
	@ApiModelProperty(name = "생년월일")
	private Date dateBirth;
	@ApiModelProperty(name = "주소")
	private String address; 
	@ApiModelProperty(name = "영문이름")
	private String englishName; 
	@ApiModelProperty(name = "성별")
	private String gender;
	@ApiModelProperty(name = "병역사항")
	private String militaryService;
	@ApiModelProperty(name = "보훈사항")
	private String veteransAffairs;
	@ApiModelProperty(name = "장애여부")
	private String disabled;

	
	@ApiModelProperty(name="어학,자격증")
	private List<CertificatePostReq> certificates;
	
	@ApiModelProperty(name="활동사항")
	private List<ActivityPostReq> activity;
	
	@ApiModelProperty(name="최종학력")
	private List<FinalEducationPostReq> finaleducation;
	
}
