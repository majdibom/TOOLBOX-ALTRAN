package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import com.altran.toolsbox.qualitymanagement.model.Week;

/**
 * Represents the interface of week service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface WeekService {

	List<Week> findAll();

	Week findById(Long id);

}
