package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.FIRSTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.LASTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.DESCRIPTION;
import static com.altran.toolsbox.util.constant.ColumnConstants.CONTINGENCYPLAN;
import static com.altran.toolsbox.util.constant.ColumnConstants.MITIGATIONAPPROACH;
import static com.altran.toolsbox.util.constant.ColumnConstants.TYPEACTION;
import static com.altran.toolsbox.util.constant.FilterConstants.RISK_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_FIND_ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_NOT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_NOT_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_UPDATED;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTION_FILTER;
import static com.altran.toolsbox.util.constant.ColumnConstants.RISKNATURE;
import static com.altran.toolsbox.util.constant.ColumnConstants.DETECTIONDATE;
import static com.altran.toolsbox.util.constant.ColumnConstants.CLOSUREDATE;
import static com.altran.toolsbox.util.constant.ColumnConstants.RISKSTATUS;
import static com.altran.toolsbox.util.constant.ColumnConstants.PROBABILITY;
import static com.altran.toolsbox.util.constant.ColumnConstants.SEVERITY;
import static com.altran.toolsbox.util.constant.ColumnConstants.EXPOSURE;



import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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

import com.altran.toolsbox.qualitymanagement.model.Risk;
import com.altran.toolsbox.qualitymanagement.service.RiskService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of risk
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/risks")
public class RiskController {

	private final RiskService riskService;

	private GenericResponse<Risk> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of RiskController
	 * 
	 * @param riskService the service interface of risk
	 */
	@Autowired
	public RiskController(RiskService riskService) {
		this.riskService = riskService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with risk as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Risk> objectResponse) {
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
	 * Gets the list of all Risks by page
	 * 
	 * @param pageable pagination information
	 * @return list of all Risks by page
	 */
	@GetMapping
	public MappingJacksonValue getRisksByPage(Pageable pageable) {
		Page<Risk> riskList = riskService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter riskFilter = SimpleBeanPropertyFilter.serializeAllExcept(CONTINGENCYPLAN,
				MITIGATIONAPPROACH);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(RISK_FILTER, riskFilter)
				.addFilter(USER_FILTER, userFilter).addFilter(ACTION_FILTER, actionFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue riskMapping = new MappingJacksonValue(riskList);
		riskMapping.setFilters(filters);
		return riskMapping;
	}

	/**
	 * gets one risk by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id : the id of the risk
	 * @return ResponseEntity: the object or the error to display when getting risk
	 *         by id with HttpStatus status code
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getRiskById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with risk object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(riskService.findById(id));
			/** Filtering data to send **/
			// Filter the risk object
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();
			// Add filters to filter provider
			filterProvider.addFilter(RISK_FILTER, SimpleBeanPropertyFilter.serializeAll());
			filterProvider.addFilter(USER_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME));
			filterProvider.addFilter(ACTION_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION));
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue actionMapping = new MappingJacksonValue(objectResponse);
			actionMapping.setFilters(filterProvider);
			return ResponseEntity.status(HttpStatus.OK).body(actionMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new risk
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param risk the risk to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         risk with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createRisk(@RequestBody Risk risk) {
		try {
			riskService.create(risk);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(RISK_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one risk
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param risk the new risk object with the new values
	 * @param id   the id of the risk
	 * @return ResponseEntity: the message or the error to display after updating
	 *         risk with HttpStatus status code
	 */
	@PutMapping("/{id}")
	public ResponseEntity<GenericResponse<String>> updateRisk(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Risk risk) {

		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			riskService.update(risk, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(RISK_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one risk
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this risk is used and any other exception
	 * 
	 * @param id the of the deleted risk
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         risk with HttpStatus status code
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<GenericResponse<String>> deleteRisk(@PathVariable(value = "id") Long id) {
		try {
			riskService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(RISK_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}
	/**
	 * Gets the list of all risks of one responsible
	 * 
	 * @param username userName of the responsible
	 * @param pageable pagination information
	 * @return list of all risks of the responsible
	 */
	@GetMapping(value = "/responsible/{username}")
	public MappingJacksonValue getRisksByRiskResponsable(@PathVariable String username, Pageable pageable) {
		Page<Risk> riskList = riskService.findByRiskPilote(username, pageable);
		// Filter the action object
		SimpleBeanPropertyFilter riskFilter = SimpleBeanPropertyFilter.filterOutAllExcept(TYPEACTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(RISK_FILTER, riskFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue risksMapping = new MappingJacksonValue(riskList);
		risksMapping.setFilters(filters);
		return risksMapping;
	}
	/**
	 * Searches for actions by one term
	 * 
	 * @param term
	 * @param pageable pagination information
	 * @return term the term to base search on it
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchRisks(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Risk> riskList = riskService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the activity object
		SimpleBeanPropertyFilter riskFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID,RISKNATURE,PROBABILITY,SEVERITY,EXPOSURE,RISKSTATUS,DETECTIONDATE,CLOSUREDATE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(RISK_FILTER, riskFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue riskMapping = new MappingJacksonValue(riskList);
		riskMapping.setFilters(filters);
		return riskMapping;
	}

}
