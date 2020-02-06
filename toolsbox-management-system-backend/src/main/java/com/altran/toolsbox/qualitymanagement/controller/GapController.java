package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.*;
import static com.altran.toolsbox.util.constant.FilterConstants.*;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_FIND_ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_NOT_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_UPDATED;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.qualitymanagement.model.Gap;
import com.altran.toolsbox.qualitymanagement.service.GapService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of gap
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/gaps")
public class GapController {

	private final GapService gapService;

	private GenericResponse<Gap> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of GapController
	 * 
	 * @param gapService the service of action
	 */
	@Autowired
	public GapController(GapService gapService) {
		this.gapService = gapService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with action as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Gap> objectResponse) {
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
	 * Gets the list of all gaps by their report
	 * 
	 * @param id the id of the report
	 * @return list of all gaps by their report
	 */
	@GetMapping(value = "/report/{id}")
	public MappingJacksonValue getGapsByAuditReport(@PathVariable Long id) {
		List<Gap> gapsList = gapService.findByAuditReport(id);
		/** Filtering data to send **/
		// Filter the Gap object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(GAP_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(AUDITREPORT, JUSTIFICATION,
				IMPROVEMENTCLUE, IDENTIFIEDCAUSES, AUDITREPORT));
		filterProvider.addFilter(ACTION_FILTER,
				SimpleBeanPropertyFilter.serializeAllExcept(PRIORITY, IDENTIFICATIONDATE, 
						PERFORMANCECRITERIA, EFFICIENCYCRITERIA,
						PROCESSIMPACTS, GAP, ORIGIN, DESCRIPTION, TYPEACTION, ACTIONSTATUS, RESPONSIBLEACTION));
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue gapsMapping = new MappingJacksonValue(gapsList);
		gapsMapping.setFilters(filterProvider);
		return gapsMapping;
	}

	/**
	 * gets one gap by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the gap
	 * @return ResponseEntity: the object or the error to display when getting gap
	 *         by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getGapById(@PathVariable Long id) {
		try {
			// Set the response with gap object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(gapService.findById(id));
			/** Filtering data to send **/
			// Filter the gap object
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();
			filterProvider.addFilter(GAP_FILTER, SimpleBeanPropertyFilter.serializeAll());
			filterProvider.addFilter(AUDITREPORT_FILTER,
					SimpleBeanPropertyFilter.serializeAllExcept(EXAMINEDPOINTS, STRONGPOINTS, GAPS, VALIDATIONAUDITOR,
							VALIDATIONAUDITORDATE, VALIDATIONAUDITED, VALIDATIONAUDITEDDATE));
			filterProvider.addFilter(AUDIT_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(RISKS, ISSUES, WEEK,
					AUDITREPORT, DURATION, PROJECT, PROCESS));
			filterProvider.addFilter(PROCESS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID));
			filterProvider.addFilter(ACTION_FILTER,
					SimpleBeanPropertyFilter.serializeAllExcept(PRIORITY, IDENTIFICATIONDATE, 
							PERFORMANCECRITERIA, EFFICIENCYCRITERIA, 
							 PROCESSIMPACTS, GAP, ORIGIN, REALIZATIONDATE));
			filterProvider.addFilter(USER_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(EMAIL, USERNAME, PASSW,
					ROLES, ACTIVITY, AUDITS, ACTIONS));
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue gapMapping = new MappingJacksonValue(objectResponse);
			gapMapping.setFilters(filterProvider);
			return ResponseEntity.status(HttpStatus.OK).body(gapMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one gap
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param gap the new gap object with the new values
	 * @param id  the id of the gap
	 * @return ResponseEntity: the message or the error to display after updating
	 *         gap report with HttpStatus status code
	 */
	@PutMapping
	public ResponseEntity<GenericResponse<String>> updateEcart(@RequestBody Gap gap,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			gapService.update(gap, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(GAP_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one gap
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this gap is used and any other exception
	 * 
	 * @param id the of the deleted gap
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         gap with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteEcart(@PathVariable Long id) {
		try {
			gapService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(GAP_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

}
