package com.altran.toolsbox.usermanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.TITLE;
import static com.altran.toolsbox.util.constant.ColumnConstants.DESCRIPTION;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTIVITY_FILTER;
import static com.altran.toolsbox.util.constant.ResponseConstants.*;

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

import com.altran.toolsbox.usermanagement.model.Activity;
import com.altran.toolsbox.usermanagement.service.ActivityService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of activity
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/activities")
public class ActivityController {

	private final ActivityService activityService;

	private GenericResponse<Activity> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of ActivityController
	 * 
	 * @param activityService the service interface of activity
	 */
	@Autowired
	public ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Activity> objectResponse) {
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
	 * Gets the list of all activities
	 * 
	 * @return list of all activities
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getActivities() {
		List<Activity> activityList = activityService.findAll();
		/** Filtering data to send **/
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue activityMapping = new MappingJacksonValue(activityList);
		activityMapping.setFilters(filters);
		return activityMapping;
	}

	/**
	 * Gets the list of all activities by page
	 * 
	 * @param pageable pagination information
	 * @return list of all activities by page
	 */
	@GetMapping
	public MappingJacksonValue getActivitiesByPage(Pageable pageable) {
		Page<Activity> activityList = activityService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue activityMapping = new MappingJacksonValue(activityList);
		activityMapping.setFilters(filters);
		return activityMapping;
	}

	/**
	 * gets one activity by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the activity
	 * @return ResponseEntity: the object or the error to display when getting
	 *         activity by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getActivityById(@PathVariable Long id) {
		try {
			// Set the response with role object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(activityService.findById(id));
			/** Filtering data to send **/
			// Filter the activity object
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();
			// Add filters to filter provider
			filterProvider.addFilter(ACTIVITY_FILTER, SimpleBeanPropertyFilter.serializeAll());
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue activityMapping = new MappingJacksonValue(objectResponse);
			activityMapping.setFilters(filterProvider);
			return ResponseEntity.status(HttpStatus.OK).body(activityMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new activity
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param activity the activity to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         activity with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createActivity(@RequestBody Activity activity) {
		try {
			activityService.create(activity);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one activity
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param activity the new activity object with the new values
	 * @param id       the id of the activity
	 * @return ResponseEntity: the message or the error to display after updating
	 *         activity with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateActivity(@RequestBody Activity activity,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			activityService.update(activity, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one activity
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this activity is used and any other exception
	 * 
	 * @param id the of the deleted activity
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         activity with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteActivity(@PathVariable(value = "id") Long id) {
		try {
			activityService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTIVITY_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for activities by one term
	 * 
	 * @param term
	 * @param pageable pagination information
	 * @return term the term to base search on it
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchActivities(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Activity> activityList = activityService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue activityMapping = new MappingJacksonValue(activityList);
		activityMapping.setFilters(filters);
		return activityMapping;
	}

}
