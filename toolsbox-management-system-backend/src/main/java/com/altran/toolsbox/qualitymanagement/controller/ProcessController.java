package com.altran.toolsbox.qualitymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.altran.toolsbox.util.constant.FilterConstants.PROCESS_FILTER;

import java.util.List;

import com.altran.toolsbox.qualitymanagement.model.Process;
import com.altran.toolsbox.qualitymanagement.service.ProcessService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of process
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/process")
public class ProcessController {

	private final ProcessService processService;

	/**
	 * Constructor of ProcessController
	 * 
	 * @param processService the service of process
	 */
	@Autowired
	public ProcessController(ProcessService processService) {
		this.processService = processService;
	}

	/**
	 * Gets the list of all process
	 * 
	 * @return list of all process
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getAllProcess() {
		List<Process> processList = processService.findAll();
		/** Filtering data to send **/
		// Filter the action object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(PROCESS_FILTER, SimpleBeanPropertyFilter.serializeAll());
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue processesMapping = new MappingJacksonValue(processList);
		processesMapping.setFilters(filterProvider);
		return processesMapping;
	}

}
