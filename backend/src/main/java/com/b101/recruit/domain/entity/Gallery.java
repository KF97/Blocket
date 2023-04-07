package com.b101.recruit.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * aws 연동 파일 업로드 모델 정의.
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@Setter
@Entity
public class Gallery{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String filePath;

    @Column
    private Long pid;

    @Column
    private Long sid;

    @Column
    private String sortation;

    @Builder
    public Gallery(Long id, String title, String filePath, Long pid, Long sid, String sortation) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.pid = pid;
        this.sid = sid;
        this.sortation = sortation;
    }
    
}
