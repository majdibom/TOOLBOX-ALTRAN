package com.altran.toolsbox.qualitymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.AuditReport;
import com.altran.toolsbox.qualitymanagement.model.Gap;

/**
 * Represents repository of gap
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface GapRepository extends JpaRepository<Gap, Long> {

	Optional<List<Gap>> findByAuditReport(AuditReport auditReport);

}
