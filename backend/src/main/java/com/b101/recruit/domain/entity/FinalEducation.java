package com.b101.recruit.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 최종학력 모델 정의.
 */

@Entity
@Getter
@Setter
public class FinalEducation extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "personal_info_id")
	private PersonalInfo personalinfo; // 신상정보 id
	
	private String name; // 최종 학교 이름
	private String sortation; // 학교 구분
	private String grades; // 성적
	
	private Long userId; //user PK
	
}
