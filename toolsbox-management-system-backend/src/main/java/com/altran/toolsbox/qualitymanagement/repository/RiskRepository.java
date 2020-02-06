package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.altran.toolsbox.qualitymanagement.model.Risk;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of risk
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
public interface RiskRepository extends JpaRepository<Risk, Long> {
	Page<Risk> findByRiskPilote(User user, Pageable pageable);
}
