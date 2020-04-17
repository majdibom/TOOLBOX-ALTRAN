package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.qualitymanagement.model.Audit;

/**
 * Represents the interface of audit service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface AuditService {

	List<Audit> findAll();

	Page<Audit> findAllByPage(Pageable pageable);

	Page<Audit> findByPrimaryAuditor(String username, Pageable pageable);

	Page<Audit> findByAccompanyingAuditor(String username, Pageable pageable);

	Page<Audit> findByAudited(String username, Pageable pageable);

	Page<Audit> simpleSearch(String term, Pageable pageable);

	Audit findById(Long id);

	Audit create(Audit audit);

	Audit update(Audit audit, Long id);

	void delete(Long id);

	boolean existsById(Long id);

}
