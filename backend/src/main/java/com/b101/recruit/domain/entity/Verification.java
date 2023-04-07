package com.b101.recruit.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.b101.recruit.domain.dto.GalleryDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 검증 모델 정의.
 */

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class Verification extends BaseEntity {
	
	//얘 사용하려면 personalinfo에서 어학,활동상태,최종학력 등록 시에 verification 테이블에 등록해줘야 함
	@ManyToOne
	@JoinColumn(name = "personal_info_id")
	private PersonalInfo personalinfo; // 신상정보 id
	
	private Date registrationDate; // 등록일
	private String currentStatus; // 현재상태(승인대기, 승인완료, 거절)
	private Long userId;
	
    @OneToOne
    @JoinColumn(name = "gallery_id", nullable = false)
    private Gallery gallery;
	
	@Column(columnDefinition = "LONGTEXT")
	private String reasonsRejection; // 반려사유
	
	@PrePersist
	public void createdAt() {
		this.registrationDate = new Date();
	}
	
}
