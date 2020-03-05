package com.altran.toolsbox.qualitymanagement.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.altran.toolsbox.qualitymanagement.model.Probability;
import com.altran.toolsbox.qualitymanagement.model.Risk;
import com.altran.toolsbox.qualitymanagement.model.RiskNature;
import com.altran.toolsbox.qualitymanagement.model.RiskStatus;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of risk
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
public interface RiskRepository extends JpaRepository<Risk, Long> {
	Page<Risk> findByRiskPilote(User user, Pageable pageable);

	@Query("SELECT t FROM Risk t where LOWER (t.riskNature) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.probability) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.severity) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.riskStatus) LIKE CONCAT('%', LOWER ( :term ), '%')")
	public Page<Risk> simpleSearch(@Param("term") String term, Pageable pageable);

	@Query("SELECT DISTINCT t FROM Risk t where ((t.probability in (:probabilities))"
			+ " OR ((:probabilities) is null)) AND ((t.riskNature in (:riskNatures))"
			+ " OR ((:riskNatures) is null)) AND ((t.riskStatus in (:status))" + " OR ((:status) is null)) ")
	public Page<Risk> advancedSearch(@Param("probabilities") Set<Probability> probabilities,
			@Param("riskNatures") Set<RiskNature> riskNatures, @Param("status") Set<RiskStatus> status,
			Pageable pageable);
}
