package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.AuditReport;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of audit report
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface AuditReportRepository extends JpaRepository<AuditReport, Long> {

	Page<AuditReport> findByAuditor(User user, Pageable pageable);
}
