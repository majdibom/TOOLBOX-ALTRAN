package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ACCOMPANYINGAUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.ACTIONS;
import static com.altran.toolsbox.util.constant.ColumnConstants.ACTIVITY;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITED;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITREPORT;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITS;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITTHEME;
import static com.altran.toolsbox.util.constant.ColumnConstants.DELIVERYMODEL;
import static com.altran.toolsbox.util.constant.ColumnConstants.DURATION;
import static com.altran.toolsbox.util.constant.ColumnConstants.EMAIL;
import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.ISSUES;
import static com.altran.toolsbox.util.constant.ColumnConstants.PASSW;
import static com.altran.toolsbox.util.constant.ColumnConstants.PRIMARYAUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.REFERENCE;
import static com.altran.toolsbox.util.constant.ColumnConstants.RISKS;
import static com.altran.toolsbox.util.constant.ColumnConstants.ROLES;
import static com.altran.toolsbox.util.constant.ColumnConstants.USERNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.WEEK;
import static com.altran.toolsbox.util.constant.ResponseConstants.WEEK_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.WEEK_FIND_ERROR;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDIT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROCESS_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROJECT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.WEEK_FILTER;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.qualitymanagement.model.Week;
import com.altran.toolsbox.qualitymanagement.service.WeekService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
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
@RequestMapping(value = "/weeks")
public class WeekController {

	private final WeekService weekService;

	private GenericResponse<Week> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of WeekController
	 * 
	 * @param weekService the service interface of week
	 */
	@Autowired
	public WeekController(WeekService weekService) {
		this.weekService = weekService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with action as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Week> objectResponse) {
		this.objectResponse = objectResponse;
	}

	/**
	 * Changes message response object for sending message
	 * 
	 * @param messageResponse generic response with string as object.
	 */
	@Autowired
	public void setMessageResponse(GenericResponse<String> messageResponse) {
		this.messageResponse = messageResponse;
	}

	/**
	 * Gets the list of all weeks
	 * 
	 * @return list of all weeks
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getAllWeeks() {
		List<Week> weekList = weekService.findAll();
		/** Filtering data to send **/
		// Filter the week object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(AUDIT_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(AUDITREPORT, WEEK, REFERENCE,
				RISKS, DURATION, ISSUES, PRIMARYAUDITOR, ACCOMPANYINGAUDITOR, AUDITED, AUDITTHEME));
		filterProvider.addFilter(PROJECT_FILTER,
				SimpleBeanPropertyFilter.serializeAllExcept(ID, ACTIVITY, DELIVERYMODEL));
		filterProvider.addFilter(PROCESS_FILTER, SimpleBeanPropertyFilter.serializeAll());
		filterProvider.addFilter(WEEK_FILTER, SimpleBeanPropertyFilter.serializeAll());
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue weeksMapping = new MappingJacksonValue(weekList);
		weeksMapping.setFilters(filterProvider);
		return weeksMapping;
	}

	/**
	 * Gets the list of all weeks for audits
	 * 
	 * @return list of all weeks
	 */
	@GetMapping(value = "/all/audits")
	public MappingJacksonValue getAllWeeksAudit() {
		List<Week> weekList = weekService.findAll();
		/** Filtering data to send **/
		// Filter the week object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(WEEK_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(AUDITS));
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue weeksMapping = new MappingJacksonValue(weekList);
		weeksMapping.setFilters(filterProvider);
		return weeksMapping;
	}

	/**
	 * gets one week by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the week
	 * @return ResponseEntity: the object or the error to display when getting week
	 *         by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getWeekById(@PathVariable Long id) {
		try {
			// Set the response with action object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(weekService.findById(id));
			/** Filtering data to send **/
			// Filter the week object
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();
			filterProvider.addFilter(AUDIT_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(AUDITREPORT, RISKS,
					ISSUES, AUDITED, WEEK, REFERENCE, AUDITTHEME));
			filterProvider.addFilter(PROJECT_FILTER,
					SimpleBeanPropertyFilter.serializeAllExcept(ID, ACTIVITY, DELIVERYMODEL));
			filterProvider.addFilter(PROCESS_FILTER, SimpleBeanPropertyFilter.serializeAll());
			filterProvider.addFilter(USER_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID, EMAIL, USERNAME,
					PASSW, ROLES, ACTIVITY, AUDITS, ACTIONS));
			filterProvider.addFilter(WEEK_FILTER, SimpleBeanPropertyFilter.serializeAll());
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue weeksMapping = new MappingJacksonValue(objectResponse);
			weeksMapping.setFilters(filterProvider);
			return ResponseEntity.status(HttpStatus.OK).body(weeksMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(WEEK_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(WEEK_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

}
