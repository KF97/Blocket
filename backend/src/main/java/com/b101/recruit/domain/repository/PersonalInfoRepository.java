package com.b101.recruit.domain.repository;

import com.b101.recruit.reponse.PersonalInfoPostRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.PersonalInfo;

import java.util.List;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {

	List<PersonalInfo> findByUserId(long userId);
	PersonalInfo findOneById(Long id);
	
}
