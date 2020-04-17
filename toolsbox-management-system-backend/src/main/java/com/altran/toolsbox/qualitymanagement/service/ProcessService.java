package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import com.altran.toolsbox.qualitymanagement.model.Process;

/**
 * Represents the interface of process service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface ProcessService {

	List<Process> findAll();

	Process findById(Long id);

}
