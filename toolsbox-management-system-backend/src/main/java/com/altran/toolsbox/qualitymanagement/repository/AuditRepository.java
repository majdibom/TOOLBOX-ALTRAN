package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Audit;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of audit
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

	Page<Audit> findByPrimaryAuditor(User user, Pageable pageable);

	Page<Audit> findByAccompanyingAuditor(User user, Pageable pageable);

	Page<Audit> findByAudited(User user, Pageable pageable);

}
