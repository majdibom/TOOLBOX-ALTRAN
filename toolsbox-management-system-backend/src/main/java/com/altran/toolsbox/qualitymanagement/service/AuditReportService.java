package com.altran.toolsbox.qualitymanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.qualitymanagement.model.AuditReport;

/**
 * Represents the interface of audit report service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
public interface AuditReportService {

	Page<AuditReport> findByResponsableAudit(String username, Pageable pageable);

	AuditReport findById(Long id);

	AuditReport create(AuditReport auditReport);

	AuditReport update(AuditReport auditReport, Long id);

	void validateReport(String validation, String validator, Long id);

	void delete(Long id);
}
