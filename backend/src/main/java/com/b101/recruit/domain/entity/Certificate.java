package com.b101.recruit.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 자격증 모델 정의.
 */

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class Certificate extends BaseEntity {
	
	@ManyToOne
//	@JoinColumn(name = "personal_info_id", insertable = false, updatable = false)
	@JoinColumn(name = "personal_info_id")
	private PersonalInfo personalinfo; // 신상정보 id
	
	private String name; // 자격증, 어학점수명
	private String sortation; // 구분
	private String acquisitionDate; // 취득기간
	private String score; // 점수, 등급
	
	private Long userId;
	
	
}
