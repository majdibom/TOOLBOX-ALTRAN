package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import com.altran.toolsbox.qualitymanagement.model.Gap;

/**
 * Represents the interface of gap service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface GapService {
	List<Gap> findAll();

	List<Gap> findByAuditReport(Long id);

	Gap findById(Long id);

	Gap create(Gap gap);

	Gap update(Gap gap, Long id);

	void delete(Long id);

}
