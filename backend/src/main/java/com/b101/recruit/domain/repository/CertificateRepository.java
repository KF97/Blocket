package com.b101.recruit.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b101.recruit.domain.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

	Optional<List<Certificate>> findByPersonalinfo_id(Long personal_info_id);

	Optional<Certificate> findById(Long certificateId);
}
