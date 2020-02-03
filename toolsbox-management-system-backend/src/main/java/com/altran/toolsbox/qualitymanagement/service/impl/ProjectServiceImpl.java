package com.altran.toolsbox.qualitymanagement.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Project;
import com.altran.toolsbox.qualitymanagement.repository.ProjectRepository;
import com.altran.toolsbox.qualitymanagement.service.ProjectService;

/**
 * Represents implementation of project service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;

	/**
	 * Constructor of ProjectServiceImpl
	 * 
	 * @param projectRepository the repository of gap
	 */
	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	/**
	 * Gets the list of all projects
	 * 
	 * @return list of all projects
	 */
	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	/**
	 * Creates a new project
	 * 
	 * @param project the project to create
	 * @return the created project
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Project create(Project project) {
		if (project.getId() != null && projectRepository.existsById(project.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return projectRepository.save(project);
	}

}
