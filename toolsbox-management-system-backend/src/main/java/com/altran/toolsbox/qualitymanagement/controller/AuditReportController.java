package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ACTIONS;
import static com.altran.toolsbox.util.constant.ColumnConstants.ACTIVITY;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITED;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITREPORT;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITS;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITTHEME;
import static com.altran.toolsbox.util.constant.ColumnConstants.DELIVERYMODEL;
import static com.altran.toolsbox.util.constant.ColumnConstants.DURATION;
import static com.altran.toolsbox.util.constant.ColumnConstants.EMAIL;
import static com.altran.toolsbox.util.constant.ColumnConstants.EXAMINEDPOINTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.FIRSTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.GAPS;
import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.IDENTIFIEDCAUSES;
import static com.altran.toolsbox.util.constant.ColumnConstants.IMPROVEMENTCLUE;
import static com.altran.toolsbox.util.constant.ColumnConstants.ISSUES;
import static com.altran.toolsbox.util.constant.ColumnConstants.JUSTIFICATION;
import static com.altran.toolsbox.util.constant.ColumnConstants.LASTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.PASSW;
import static com.altran.toolsbox.util.constant.ColumnConstants.PROCESSIMPACTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.REALIZATIONDATE;
import static com.altran.toolsbox.util.constant.ColumnConstants.REFERENCE;
import static com.altran.toolsbox.util.constant.ColumnConstants.RISKS;
import static com.altran.toolsbox.util.constant.ColumnConstants.ROLES;
import static com.altran.toolsbox.util.constant.ColumnConstants.STRONGPOINTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.USERNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITED;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITORDATE;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDITREPORT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDIT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.GAP_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROCESS_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROJECT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.WEEK_FILTER;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_FIND_ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_NOT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_NOT_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDITREPORT_UPDATED;

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

import com.altran.toolsbox.qualitymanagement.model.AuditReport;
import com.altran.toolsbox.qualitymanagement.service.AuditReportService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of audit report
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/audit-reports")
public class AuditReportController {

	private final AuditReportService auditReportService;

	private GenericResponse<AuditReport> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of AuditReportController
	 * 
	 * @param actionService
	 *            the service of audit report
	 */
	@Autowired
	public AuditReportController(AuditReportService auditReportService) {
		this.auditReportService = auditReportService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse
	 *            generic response with audit report as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<AuditReport> objectResponse) {
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
	 * Gets the list of all audit report by their responsible by page
	 * 
	 * @param username
	 *            audit responsible userName
	 * @param pageable
	 *            pagination information
	 * @return list of all audit report by their responsible by page
	 */
	@GetMapping(value = "/responsable-audite/{username}")
	public MappingJacksonValue getAuditReportsByResponsableAudit(@PathVariable String username, Pageable pageable) {
		Page<AuditReport> auditReportList = auditReportService.findByResponsableAudit(username, pageable);
		/** Filtering data to send **/
		// Filter the audit report object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(AUDITREPORT_FILTER,
				SimpleBeanPropertyFilter.serializeAllExcept(REALIZATIONDATE, DURATION, EXAMINEDPOINTS, STRONGPOINTS,
						GAPS, VALIDATIONAUDITOR, VALIDATIONAUDITED, VALIDATIONAUDITORDATE, AUDITOR));
		filterProvider.addFilter(AUDIT_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(AUDITREPORT, RISKS, ISSUES,
				AUDITED, PROCESSIMPACTS, REFERENCE, AUDITTHEME));
		filterProvider.addFilter(WEEK_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(AUDITS, ID));
		filterProvider.addFilter(PROJECT_FILTER,
				SimpleBeanPropertyFilter.serializeAllExcept(ID, ACTIVITY, DELIVERYMODEL));
		filterProvider.addFilter(PROCESS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID));
		filterProvider.addFilter(USER_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID, EMAIL, USERNAME, PASSW,
				ROLES, ACTIVITY, AUDITS, ACTIONS));
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue auditReportsMapping = new MappingJacksonValue(auditReportList);
		auditReportsMapping.setFilters(filterProvider);
		return auditReportsMapping;
	}

	/**
	 * gets one audit report by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id
	 *            the id of the audit report
	 * @return ResponseEntity: the object or the error to display when getting audit
	 *         report by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getAuditReportById(@PathVariable Long id) {
		try {
			// Set the response with role object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(auditReportService.findById(id));
			/** Filtering data to send **/
			// Filter the audit report object
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();
			filterProvider.addFilter(AUDITREPORT_FILTER, SimpleBeanPropertyFilter.serializeAll());
			filterProvider.addFilter(AUDIT_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(ID));
			filterProvider.addFilter(PROJECT_FILTER,
					SimpleBeanPropertyFilter.serializeAllExcept(ID, ACTIVITY, DELIVERYMODEL));
			filterProvider.addFilter(PROCESS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ID));
			filterProvider.addFilter(GAP_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(JUSTIFICATION,
					IMPROVEMENTCLUE, IDENTIFIEDCAUSES, ACTIONS, AUDITREPORT));
			filterProvider.addFilter(USER_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME));
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue auditReportMapping = new MappingJacksonValue(objectResponse);
			auditReportMapping.setFilters(filterProvider);
			return ResponseEntity.status(HttpStatus.OK).body(auditReportMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_FIND_ERROR));
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
	public ResponseEntity<GenericResponse<String>> createAuditReport(@RequestBody AuditReport auditReport) {
		try {
			auditReportService.create(auditReport);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one audit report
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param AuditReport
	 *            the new audit report object with the new values
	 * @param id
	 *            the id of the audit report
	 * @return ResponseEntity: the message or the error to display after updating
	 *         audit report with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateAuditReport(@RequestBody AuditReport auditReport,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			auditReportService.update(auditReport, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	@GetMapping(value = "/validate/{id}/{validator}/{validation}")
	public boolean validateReport(@PathVariable Long id, @PathVariable String validator,
			@PathVariable String validation) {
		auditReportService.validateReport(validation, validator, id);
		return true;
	}

	/**
	 * Deletes one audit report
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this audit report is used and any other exception
	 * 
	 * @param id
	 *            the of the deleted audit report
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         audit report with HttpStatus status code
	 */
	@DeleteMapping(value = "{id}")
	public ResponseEntity<GenericResponse<String>> deleteAuditReport(@PathVariable Long id) {
		try {
			auditReportService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDITREPORT_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

}
