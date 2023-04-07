package com.b101.recruit.domain.repository;

import java.util.Optional;

import com.b101.recruit.domain.entity.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.Verification;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long>{

//	Optional<Verification> findByFileId(Long fileId);

	Page<Verification> findByCurrentStatusContaining(Pageable pageable, String verified);
	Page<Verification> findAll(Pageable pageable);
	Optional<Verification> findByGalleryId(Long fileId);
	String findCurrentStatusByGalleryId(Long gId);
	void deleteByGallery(Gallery gallery);
//	Verification findBySid(Long cId);
	Page<Verification> findByGallery_SortationNotLike(Pageable pageable, String prop);
	Page<Verification> findByCurrentStatusContainingAndGallery_SortationNotLike(Pageable pageable, String verified,
			String prop);
}