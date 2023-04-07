package com.b101.recruit.domain.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.Gallery;
import com.b101.recruit.domain.entity.PersonalInfo;
import com.b101.recruit.domain.entity.QGallery;
import com.b101.recruit.domain.entity.QPersonalInfo;
import com.b101.recruit.domain.entity.QVerification;
import com.b101.recruit.domain.entity.Verification;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class JpaVerificationRepository {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QVerification qVerification = QVerification.verification;
	QGallery qGallery = QGallery.gallery;
	QPersonalInfo qPersonalInfo = QPersonalInfo.personalInfo;

//	@ManyToOne
//	@JoinColumn(name = "personal_info_id")
//	private PersonalInfo personalinfo; // 신상정보 id
//	
//	private Date registrationDate; // 등록일
//	private String currentStatus; // 현재상태(승인대기, 승인완료, 거절)
//	private Long userId;
//	
//    @OneToOne
//    @JoinColumn(name = "gallery_id", nullable = false)
//    private Gallery gallery;
//	
//	@Column(columnDefinition = "LONGTEXT")
//	private String reasonsRejection; // 반려사유

//	@Transactional
//	public Page<Verification> findBySortationNotContaining(Pageable pageable, String prop) {
//
//		List<Verification> verification = jpaQueryFactory
//				.select(Projections.constructor(Verification.class, qVerification.id, qVerification.personalinfo,
//						qVerification.userId, qVerification.currentStatus, qVerification.registrationDate,
//						qVerification.reasonsRejection))
//				.from(qVerification).where(qVerification.gallery.sortation.(userId)).offset(pageable.getOffset())
//				.limit(pageable.getPageSize()).fetch();
//
//	}

}