package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	 * @param projectRepository
	 *            the repository of gap
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
	 * @param project
	 *            the project to create
	 * @return the created project
	 * @throws EntityExistsException
	 *             if there is already existing entity with such ID
	 */
	@Override
	public Project create(Project project) {
		if (project.getId() != null && projectRepository.existsById(project.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return projectRepository.save(project);
	}

	/**
	 * Updates one project
	 * 
	 * @param id
	 *            the id of the project
	 * @param action
	 *            the new project object with the new values
	 * @return the updated project
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public Project update(Project project, Long id) {
		if (id != null && !projectRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		project.setId(id);
		return projectRepository.save(project);
	}

	/**
	 * Deletes one project
	 * 
	 * @param id
	 *            the of the deleted project
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !projectRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		projectRepository.deleteById(id);
	}

	/**
	 * Gets the list of all Project by page
	 * 
	 * @return list of all Project by page
	 */
	@Override
	public Page<Project> findAllByPage(Pageable pageable) {
		return projectRepository.findAll(pageable);
	}

	/**
	 * gets one project by his id
	 * 
	 * @param id
	 *            the id of the project
	 * @return project object with the same id
	 * @throws NoSuchElementException
	 *             if no element is present with such ID
	 */
	@Override
	public Project findById(Long id) {
		Optional<Project> project = projectRepository.findById(id);
		if (project.isPresent())
			return project.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

}
