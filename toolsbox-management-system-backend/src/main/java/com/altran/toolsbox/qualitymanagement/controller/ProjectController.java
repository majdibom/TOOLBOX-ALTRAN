package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTIVITY_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.DELIVERYMODEL_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROJECT_FILTER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.qualitymanagement.model.Project;
import com.altran.toolsbox.qualitymanagement.service.ProjectService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of project
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

	private final ProjectService projectService;

	/**
	 * Constructor of ActionController
	 * 
	 * @param projectService the service of project
	 */
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * Gets the list of all projects
	 * 
	 * @return list of all projects
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getAllProjects() {
		List<Project> projectList = projectService.findAll();
		/** Filtering data to send **/
		// Filter the action object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(PROJECT_FILTER, SimpleBeanPropertyFilter.serializeAll());
		filterProvider.addFilter(ACTIVITY_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID));
		filterProvider.addFilter(DELIVERYMODEL_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID));
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue projectsMapping = new MappingJacksonValue(projectList);
		projectsMapping.setFilters(filterProvider);
		return projectsMapping;
	}

}
