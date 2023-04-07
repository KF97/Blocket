package com.b101.recruit.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationDto {
	private Long id; // 검증 ID
	private Long personalInfoId;
	private Long userId;
	private Long galleryId; // 파일 ID
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date registrationDate;
	
	private String currentStatus; // 현재상태(대기중, 승인완료, 거절)
	private String reasonsRejection; // 반려사유
	
//	public Gallery toEntity(){
//        Gallery build = Gallery.builder()
//                .id(id)
//                .title(title)
//                .filePath(filePath)
//                .pid(pid)
//                .sid(sid)
//                .sortation(sortation)
//                .build();
//        return build;
//    }

    @Builder
    public VerificationDto(Long id, Long personalInfoId, Long userId, Long galleryId, Date registrationDate, String currentStatus, String reasonsRejection) {
        this.id = id;
        this.personalInfoId = personalInfoId;
        this.userId = userId;
        this.galleryId = galleryId;
        this.registrationDate = registrationDate;
        this.currentStatus = currentStatus;
        this.reasonsRejection = reasonsRejection;

    }
}
