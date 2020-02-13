package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	@Query("SELECT t FROM Risk t where LOWER (t.riskNature) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.probability) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.severity) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.exposure) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.riskStatus) LIKE CONCAT('%', LOWER ( :term ), '%')")
	public Page<Risk> simpleSearch(@Param("term") String term, Pageable pageable);
}
