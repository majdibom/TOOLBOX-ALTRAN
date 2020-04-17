package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Audit;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of audit
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

	Page<Audit> findByPrimaryAuditor(User user, Pageable pageable);

	Page<Audit> findByAccompanyingAuditor(User user, Pageable pageable);

	Page<Audit> findByAudited(User user, Pageable pageable);

	@Query("SELECT t FROM Audit t where LOWER (t.process) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.project) LIKE CONCAT('%', LOWER ( :term ), '%')")
	public Page<Audit> simpleSearch(@Param("term") String term, Pageable pageable);
}
