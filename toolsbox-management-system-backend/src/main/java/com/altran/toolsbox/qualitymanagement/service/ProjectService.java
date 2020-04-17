package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.qualitymanagement.model.Project;

/**
 * Represents the interface of project service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface ProjectService {

	List<Project> findAll();

	Project create(Project project);

	Project update(Project project, Long id);

	void delete(Long id);

	Page<Project> findAllByPage(Pageable pageable);

	Project findById(Long id);
}
