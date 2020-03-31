package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.TITLE;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTIVITY_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.DELIVERYMODEL_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROJECT_FILTER;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_FIND_ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_NOT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_NOT_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.PROJECT_UPDATED;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.qualitymanagement.model.Project;
import com.altran.toolsbox.qualitymanagement.service.ProjectService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of project
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
	private final ProjectService projectService;

	private GenericResponse<Project> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of ProjectController
	 * 
	 * @param projectService
	 *            the service of project
	 */
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse
	 *            generic response with Project as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Project> objectResponse) {
		this.objectResponse = objectResponse;
	}

	/**
	 * Changes message response object for sending message
	 * 
	 * @param messageResponse
	 *            generic response with string as object.
	 */
	@Autowired
	public void setMessageResponse(GenericResponse<String> messageResponse) {
		this.messageResponse = messageResponse;
	}

	/**
	 * Gets the list of all projects
	 * 
	 * @return list of all Projects
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getActions() {
		List<Project> projectList = projectService.findAll();
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		SimpleBeanPropertyFilter deliveryModelFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(DELIVERYMODEL_FILTER, deliveryModelFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue projectsMapping = new MappingJacksonValue(projectList);
		projectsMapping.setFilters(filters);
		return projectsMapping;
	}

	/**
	 * Gets the list of all Projects by page
	 * 
	 * @param pageable
	 *            pagination information
	 * @return list of all Projects by page
	 */
	@GetMapping
	public MappingJacksonValue getProjectsByPage(Pageable pageable) {
		Page<Project> projectList = projectService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		SimpleBeanPropertyFilter deliveryModelFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(DELIVERYMODEL_FILTER, deliveryModelFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue projectsMapping = new MappingJacksonValue(projectList);
		projectsMapping.setFilters(filters);
		return projectsMapping;
	}

	/**
	 * gets one project by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id
	 *            the id of the project
	 * @return ResponseEntity: the object or the error to display when getting
	 *         project by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getProjectById(@PathVariable Long id) {
		try {
			// Set the response with role object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(projectService.findById(id));
			/** Filtering data to send **/
			// Filter the Project object
			SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.serializeAll();
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			SimpleBeanPropertyFilter deliveryModelFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(PROJECT_FILTER, projectFilter)
					.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(DELIVERYMODEL_FILTER, deliveryModelFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue projectMapping = new MappingJacksonValue(objectResponse);
			projectMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(projectMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new project
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param project
	 *            the project to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         project with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createProject(@RequestBody Project project) {
		try {
			projectService.create(project);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(PROJECT_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one project
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param project
	 *            the new project object with the new values
	 * @param id
	 *            the id of the project
	 * @return ResponseEntity: the message or the error to display after updating
	 *         project with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateProject(@RequestBody Project project,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			projectService.update(project, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(PROJECT_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one project
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this project is used and any other exception
	 * 
	 * @param id
	 *            the of the deleted project
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         project with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteProject(@PathVariable(value = "id") Long id) {
		try {
			projectService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(PROJECT_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(PROJECT_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}
}
