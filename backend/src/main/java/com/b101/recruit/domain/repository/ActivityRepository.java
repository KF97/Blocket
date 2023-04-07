package com.b101.recruit.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.Activity;

//@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

	Optional<List<Activity>> findAllByPersonalinfo_Id(Long personal_info_id);
	
}
