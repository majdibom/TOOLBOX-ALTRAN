package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.qualitymanagement.model.Exposure;
import com.altran.toolsbox.qualitymanagement.model.Probability;
import com.altran.toolsbox.qualitymanagement.model.Risk;
import com.altran.toolsbox.qualitymanagement.model.Severity;

/**
 * Represents the interface of risk service
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
public interface RiskService {

	List<Risk> findAll();

	Page<Risk> findAllByPage(Pageable pageable);
	
	Page<Risk> findByRiskPilote(String username, Pageable pageable);

	Page<Risk> simpleSearch(String term, Pageable pageable);

	Risk findById(Long id);

	Risk create(Risk risk);

	Risk update(Risk risk, Long id);

	void delete(Long id);

	Exposure calculeExposure(Probability probability, Severity severity);

}
