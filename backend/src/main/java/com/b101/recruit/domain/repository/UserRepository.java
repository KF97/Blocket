package com.b101.recruit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.User;
import com.google.common.base.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String userEmail);
	boolean existsByEmail(String userEmail);
}
