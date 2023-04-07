package com.b101.recruit.domain.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.google.common.base.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신상정보 모델 정의.
 */

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class PersonalInfo extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user; 			    // 회원 ID(FK)
	
	private Date dateBirth; 		// 생년월일
	private String address; 		// 주소
	private String englishName; 	// 영문이름
	private String gender; 			// 성별
	private String militaryService; // 병역사항
	private String veteransAffairs; // 보훈사항
	private String disabled; 		// 장애여부

	public void setUser(User user) {
		this.user=user;
	}
}
