package com.b101.recruit.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

	@Override
	List<Gallery> findAll();
	Gallery findByPidAndSidAndSortation(Long pid, Long sid, String sortation);
	Optional<Gallery> findByPidAndSortation(Long pid, String sortation);
}
