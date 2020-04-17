package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ACTIONSTATUS;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITREPORT;
import static com.altran.toolsbox.util.constant.ColumnConstants.DESCRIPTION;
import static com.altran.toolsbox.util.constant.ColumnConstants.EFFICIENCYCRITERIA;
import static com.altran.toolsbox.util.constant.ColumnConstants.EXAMINEDPOINTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.FIRSTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.GAP;
import static com.altran.toolsbox.util.constant.ColumnConstants.GAPS;
import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.IDENTIFICATIONDATE;
import static com.altran.toolsbox.util.constant.ColumnConstants.IDENTIFIEDCAUSES;
import static com.altran.toolsbox.util.constant.ColumnConstants.IMPROVEMENTCLUE;
import static com.altran.toolsbox.util.constant.ColumnConstants.JUSTIFICATION;
import static com.altran.toolsbox.util.constant.ColumnConstants.LASTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.ORIGIN;
import static com.altran.toolsbox.util.constant.ColumnConstants.PERFORMANCECRITERIA;
import static com.altran.toolsbox.util.constant.ColumnConstants.PRIORITY;
import static com.altran.toolsbox.util.constant.ColumnConstants.PROCESSIMPACTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.REALIZATIONDATE;
import static com.altran.toolsbox.util.constant.ColumnConstants.RESPONSIBLEACTION;
import static com.altran.toolsbox.util.constant.ColumnConstants.STRONGPOINTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.TYPEACTION;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITED;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITEDDATE;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITORDATE;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTION_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDITREPORT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDIT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.GAP_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROCESS_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_FIND_ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_NOT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_NOT_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.GAP_UPDATED;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.altran.toolsbox.qualitymanagement.model.Gap;
import com.altran.toolsbox.qualitymanagement.service.GapService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of gap
 * 
 * @author Majdi.BEN.OTHMEN
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
	 * @param gapService
	 *            the service of action
	 */
	@Autowired
	public GapController(GapService gapService) {
		this.gapService = gapService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse
	 *            generic response with action as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Gap> objectResponse) {
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
	 * Gets the list of all gaps
	 * 
	 * @return list of all gaps
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getGaps() {
		List<Gap> gapsList = gapService.findAll();
		/** Filtering data to send **/
		// Filter the Gap object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(GAP_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(AUDITREPORT, JUSTIFICATION,
				IMPROVEMENTCLUE, IDENTIFIEDCAUSES, AUDITREPORT));
		filterProvider.addFilter(ACTION_FILTER,
				SimpleBeanPropertyFilter.serializeAllExcept(PRIORITY, IDENTIFICATIONDATE, PERFORMANCECRITERIA,
						EFFICIENCYCRITERIA, PROCESSIMPACTS, GAP, ORIGIN, DESCRIPTION, TYPEACTION, ACTIONSTATUS,
						RESPONSIBLEACTION));
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue gapsMapping = new MappingJacksonValue(gapsList);
		gapsMapping.setFilters(filterProvider);
		return gapsMapping;
	}

	/**
	 * Gets the list of all gaps by their report
	 * 
	 * @param id
	 *            the id of the report
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
				SimpleBeanPropertyFilter.serializeAllExcept(PRIORITY, IDENTIFICATIONDATE, PERFORMANCECRITERIA,
						EFFICIENCYCRITERIA, PROCESSIMPACTS, GAP, ORIGIN, DESCRIPTION, TYPEACTION, ACTIONSTATUS,
						RESPONSIBLEACTION));
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
	 * @param id
	 *            the id of the gap
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
			filterProvider.addFilter(AUDIT_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(ID));
			filterProvider.addFilter(PROCESS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID));
			filterProvider.addFilter(ACTION_FILTER,
					SimpleBeanPropertyFilter.serializeAllExcept(PRIORITY, IDENTIFICATIONDATE, PERFORMANCECRITERIA,
							EFFICIENCYCRITERIA, PROCESSIMPACTS, GAP, ORIGIN, REALIZATIONDATE));
			filterProvider.addFilter(USER_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME));
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
	 * Creates a new audit report
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param auditReport
	 *            the audit report to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         audit report with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createAuditReport(@RequestBody Gap gap) {
		try {
			gapService.create(gap);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(GAP_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(GAP_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one gap
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param gap
	 *            the new gap object with the new values
	 * @param id
	 *            the id of the gap
	 * @return ResponseEntity: the message or the error to display after updating
	 *         gap report with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
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
	 * @param id
	 *            the of the deleted gap
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
