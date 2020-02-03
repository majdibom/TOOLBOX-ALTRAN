package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import com.altran.toolsbox.qualitymanagement.model.Project;

/**
 * Represents the interface of project service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
public interface ProjectService {

	List<Project> findAll();

	Project create(Project project);

}
