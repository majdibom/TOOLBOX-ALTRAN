package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ACCOMPANYINGAUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.ACTIVITY;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITED;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITREPORT;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITSTATUS;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITTHEME;
import static com.altran.toolsbox.util.constant.ColumnConstants.CREATEDAT;
import static com.altran.toolsbox.util.constant.ColumnConstants.DURATION;
import static com.altran.toolsbox.util.constant.ColumnConstants.EXAMINEDPOINTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.FIRSTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.ISSUES;
import static com.altran.toolsbox.util.constant.ColumnConstants.LASTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.NUMBER;
import static com.altran.toolsbox.util.constant.ColumnConstants.PRIMARYAUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.PROCESS;
import static com.altran.toolsbox.util.constant.ColumnConstants.PROCESSIMPACTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.PROJECT;
import static com.altran.toolsbox.util.constant.ColumnConstants.REALIZATIONDATE;
import static com.altran.toolsbox.util.constant.ColumnConstants.REFERENCE;
import static com.altran.toolsbox.util.constant.ColumnConstants.RISKS;
import static com.altran.toolsbox.util.constant.ColumnConstants.TITLE;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITED;
import static com.altran.toolsbox.util.constant.ColumnConstants.VALIDATIONAUDITOR;
import static com.altran.toolsbox.util.constant.ColumnConstants.WEEK;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTIVITY_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDITREPORT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDIT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROCESS_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROJECT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.WEEK_FILTER;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_FIND_ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_NOT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_NOT_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.AUDIT_UPDATED;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.qualitymanagement.model.Audit;
import com.altran.toolsbox.qualitymanagement.service.AuditService;
import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.service.UserService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of audit
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/audits")
public class AuditController {

	private AuditService auditService;

	private UserService userService;

	private GenericResponse<Audit> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of AuditService
	 * 
	 * @param auditService
	 *            the service interface of audit
	 */
	@Autowired
	public AuditController(AuditService auditService) {
		this.auditService = auditService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse
	 *            generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Audit> objectResponse) {
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
	 * Gets the list of all audits
	 * 
	 * @return list of all audits
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getAllAudits() {
		List<Audit> auditList = auditService.findAll();
		// Filter the audit object
		SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, AUDITREPORT, RISKS,
				DURATION, ISSUES, AUDITED, PROCESSIMPACTS, AUDITTHEME, REFERENCE, PRIMARYAUDITOR, ACCOMPANYINGAUDITOR,
				WEEK, PROJECT, PROCESS, CREATEDAT, AUDITSTATUS);
		SimpleBeanPropertyFilter weekFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NUMBER);
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, ACTIVITY);
		SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter auditReportFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, EXAMINEDPOINTS,
				REALIZATIONDATE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(AUDIT_FILTER, auditFilter)
				.addFilter(WEEK_FILTER, weekFilter).addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(PROCESS_FILTER, processFilter).addFilter(USER_FILTER, userFilter)
				.addFilter(AUDITREPORT_FILTER, auditReportFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue auditMapping = new MappingJacksonValue(auditList);
		auditMapping.setFilters(filters);
		return auditMapping;
	}

	/**
	 * Gets the list of all audits by page
	 * 
	 * @param pageable
	 *            pagination information
	 * @return list of all audits by page
	 */
	@GetMapping
	public MappingJacksonValue getAuditsByPage(Pageable pageable) {
		Page<Audit> auditList = auditService.findAllByPage(pageable);
		// Filter the audit object
		SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, AUDITREPORT, RISKS,
				DURATION, ISSUES, AUDITED, PROCESSIMPACTS, AUDITTHEME, REFERENCE, PRIMARYAUDITOR, ACCOMPANYINGAUDITOR,
				WEEK, PROJECT, PROCESS, AUDITSTATUS);
		SimpleBeanPropertyFilter weekFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NUMBER);
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, ACTIVITY);
		SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter auditReportFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, EXAMINEDPOINTS,
				REALIZATIONDATE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(AUDIT_FILTER, auditFilter)
				.addFilter(WEEK_FILTER, weekFilter).addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(PROCESS_FILTER, processFilter).addFilter(USER_FILTER, userFilter)
				.addFilter(AUDITREPORT_FILTER, auditReportFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue auditMapping = new MappingJacksonValue(auditList);
		auditMapping.setFilters(filters);
		return auditMapping;
	}

	/**
	 * Gets the list of all audits of the principal auditor
	 * 
	 * @param username
	 *            the principal auditor's user name.
	 * @param pageable
	 *            pagination information
	 * @return list of all audits of the principal auditor
	 */
	// @PreAuthorize("hasAnyAuthority('" + SHOWESPACEAUDITEUR + "')")
	@GetMapping(value = "/principal-auditor/{username}")
	public MappingJacksonValue getAuditsByAuditeurPrincipal(@PathVariable String username, Pageable pageable) {
		Page<Audit> auditList = auditService.findByPrimaryAuditor(username, pageable);
		// Filter the audit object
		SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, AUDITREPORT, RISKS,
				DURATION, ISSUES, AUDITED, PROCESSIMPACTS, AUDITTHEME, REFERENCE, PRIMARYAUDITOR, ACCOMPANYINGAUDITOR,
				WEEK, PROJECT, PROCESS, AUDITSTATUS);
		SimpleBeanPropertyFilter weekFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NUMBER);
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter auditReportFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, EXAMINEDPOINTS,
				REALIZATIONDATE, VALIDATIONAUDITOR, VALIDATIONAUDITED);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(AUDIT_FILTER, auditFilter)
				.addFilter(WEEK_FILTER, weekFilter).addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(PROCESS_FILTER, processFilter).addFilter(AUDITREPORT_FILTER, auditReportFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue auditMapping = new MappingJacksonValue(auditList);
		auditMapping.setFilters(filters);
		return auditMapping;
	}

	/**
	 * Gets the list of all audits of the accompanying auditor
	 * 
	 * @param username
	 *            the accompanying auditor's user name.
	 * @param pageable
	 *            pagination information
	 * @return list of all audits of the accompanying auditor
	 */
	// @PreAuthorize("hasAnyAuthority('" + SHOWESPACEAUDITEUR + "')")
	@GetMapping(value = "/accompanying-auditor/{username}")
	public MappingJacksonValue getAuditsByAuditeurAccompagnant(@PathVariable String username, Pageable pageable) {
		Page<Audit> auditList = auditService.findByAccompanyingAuditor(username, pageable);
		// Filter the audit object
		SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, AUDITREPORT, RISKS,
				DURATION, ISSUES, AUDITED, PROCESSIMPACTS, AUDITTHEME, REFERENCE, PRIMARYAUDITOR, ACCOMPANYINGAUDITOR,
				WEEK, PROJECT, PROCESS, AUDITSTATUS);
		SimpleBeanPropertyFilter weekFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NUMBER);
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter auditReportFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, EXAMINEDPOINTS,
				REALIZATIONDATE, VALIDATIONAUDITOR, VALIDATIONAUDITED);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(AUDIT_FILTER, auditFilter)
				.addFilter(WEEK_FILTER, weekFilter).addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(PROCESS_FILTER, processFilter).addFilter(AUDITREPORT_FILTER, auditReportFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue auditMapping = new MappingJacksonValue(auditList);
		auditMapping.setFilters(filters);
		return auditMapping;
	}

	/**
	 * Gets the list of all audits of the audited
	 * 
	 * @param username
	 *            the audited's user name.
	 * @param pageable
	 *            pagination information
	 * @return list of all audits of the audited
	 */
	// @PreAuthorize("hasAnyAuthority('" + SHOWESPACEAUDITE + "')")
	@GetMapping(value = "/audited/{username}")
	public MappingJacksonValue findByAudited(@PathVariable String username, Pageable pageable) {
		Page<Audit> auditList = auditService.findByAudited(username, pageable);
		// Filter the audit object
		SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, AUDITREPORT, RISKS,
				DURATION, ISSUES, AUDITED, PROCESSIMPACTS, AUDITTHEME, REFERENCE, PRIMARYAUDITOR, ACCOMPANYINGAUDITOR,
				WEEK, PROJECT, PROCESS, AUDITSTATUS);
		SimpleBeanPropertyFilter weekFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NUMBER);
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter auditReportFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, EXAMINEDPOINTS,
				REALIZATIONDATE, VALIDATIONAUDITOR, VALIDATIONAUDITED);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(AUDIT_FILTER, auditFilter)
				.addFilter(WEEK_FILTER, weekFilter).addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(PROCESS_FILTER, processFilter).addFilter(AUDITREPORT_FILTER, auditReportFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue auditMapping = new MappingJacksonValue(auditList);
		auditMapping.setFilters(filters);
		return auditMapping;
	}

	/**
	 * gets one audit by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id
	 *            the id of the audit
	 * @return ResponseEntity: the object or the error to display when getting audit
	 *         by id with HttpStatus status code
	 */
	// @PreAuthorize("hasAnyAuthority('" + SHOWESPACEAUDITINTERNE + "', '" +
	// UPDATEAUDIT + "')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getAuditById(@PathVariable Long id) {
		try {
			// Set the response with role object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(auditService.findById(id));
			/** Filtering data to send **/
			// Filter the action object
			SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.serializeAll();
			SimpleBeanPropertyFilter weekFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NUMBER);
			SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.serializeAll();
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
			SimpleBeanPropertyFilter auditReportFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, EXAMINEDPOINTS,
					REALIZATIONDATE);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(AUDIT_FILTER, auditFilter)
					.addFilter(WEEK_FILTER, weekFilter).addFilter(PROJECT_FILTER, projectFilter)
					.addFilter(PROCESS_FILTER, processFilter).addFilter(AUDITREPORT_FILTER, auditReportFilter)
					.addFilter(USER_FILTER, userFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue auditMapping = new MappingJacksonValue(objectResponse);
			auditMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(auditMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new audit
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param audit
	 *            the audit to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         audit with HttpStatus status code
	 */
	// @PreAuthorize("hasAuthority('" + CREATEAUDIT + "')")
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createAudit(@RequestBody Audit audit) {
		try {
			// Get connected user
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			User user = userService.findByUsername(currentPrincipalName).get();
			// add connected user to comment
			audit.setCreatedBy(user);
			auditService.create(audit);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(AUDIT_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDIT_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDIT_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}

	}

	/**
	 * Updates one audit
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param audit
	 *            the new activity object with the new values
	 * @param id
	 *            the id of the audit
	 * @return ResponseEntity: the message or the error to display after updating
	 *         audit with HttpStatus status code
	 */
	// @PreAuthorize("hasAuthority('" + UPDATEAUDIT + "')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateAudit(@RequestBody Audit audit,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDIT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			auditService.update(audit, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(AUDIT_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDIT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDIT_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one audit
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this audit is used and any other exception
	 * 
	 * @param id
	 *            the of the deleted audit
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         audit with HttpStatus status code
	 */
	// @PreAuthorize("hasAuthority('" + DELETEAUDIT + "')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteAudit(@PathVariable(value = "id") Long id) {
		try {
			auditService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(AUDIT_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDIT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(AUDIT_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for audits by one term
	 * 
	 * @param term
	 * @param pageable
	 *            pagination information
	 * @return term the term to base search on it
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchAudits(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Audit> auditList = auditService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the audit object
		SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, AUDITREPORT, RISKS,
				DURATION, ISSUES, AUDITED, PROCESSIMPACTS, AUDITTHEME, REFERENCE, PRIMARYAUDITOR, ACCOMPANYINGAUDITOR,
				WEEK, PROJECT, PROCESS, AUDITSTATUS);
		SimpleBeanPropertyFilter weekFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NUMBER);
		SimpleBeanPropertyFilter projectFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, ACTIVITY);
		SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter auditReportFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, EXAMINEDPOINTS,
				REALIZATIONDATE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(AUDIT_FILTER, auditFilter)
				.addFilter(WEEK_FILTER, weekFilter).addFilter(PROJECT_FILTER, projectFilter)
				.addFilter(PROCESS_FILTER, processFilter).addFilter(USER_FILTER, userFilter)
				.addFilter(AUDITREPORT_FILTER, auditReportFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue auditMapping = new MappingJacksonValue(auditList);
		auditMapping.setFilters(filters);
		return auditMapping;
	}
}
