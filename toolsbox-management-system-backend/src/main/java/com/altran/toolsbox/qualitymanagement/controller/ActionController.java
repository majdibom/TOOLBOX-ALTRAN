package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.ACTIONS;
import static com.altran.toolsbox.util.constant.ColumnConstants.AUDITREPORT;
import static com.altran.toolsbox.util.constant.ColumnConstants.COMMENTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.CREATEDAT;
import static com.altran.toolsbox.util.constant.ColumnConstants.CREATEDBY;
import static com.altran.toolsbox.util.constant.ColumnConstants.EFFMEASCRITERION;
import static com.altran.toolsbox.util.constant.ColumnConstants.FIRSTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.GAP;
import static com.altran.toolsbox.util.constant.ColumnConstants.ID;
import static com.altran.toolsbox.util.constant.ColumnConstants.IDENTIFIEDCAUSES;
import static com.altran.toolsbox.util.constant.ColumnConstants.IMPROVEMENTCLUE;
import static com.altran.toolsbox.util.constant.ColumnConstants.JUSTIFICATION;
import static com.altran.toolsbox.util.constant.ColumnConstants.LASTNAME;
import static com.altran.toolsbox.util.constant.ColumnConstants.PROCESSIMPACTS;
import static com.altran.toolsbox.util.constant.ColumnConstants.UPDATEDAT;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTION_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.GAP_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.PROCESS_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.RISK_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_FIND_ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_NOT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_NOT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_NOT_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.ACTION_UPDATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.COMMENT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.COMMENT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.COMMENT_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.COMMENT_NOT_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.COMMENT_NOT_DELETED;
import static com.altran.toolsbox.util.constant.ResponseConstants.COMMENT_NOT_EXIST;

import java.util.List;
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

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.Comment;
import com.altran.toolsbox.qualitymanagement.model.searchfilter.ActionFilter;
import com.altran.toolsbox.qualitymanagement.service.ActionService;
import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.service.UserService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of action
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/actions")
public class ActionController {

	private final ActionService actionService;

	private UserService userService;

	private GenericResponse<Action> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of ActionController
	 * 
	 * @param actionService
	 *            the service of action
	 */
	@Autowired
	public ActionController(ActionService actionService) {
		this.actionService = actionService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse
	 *            generic response with action as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Action> objectResponse) {
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
	 * Gets the list of all actions
	 * 
	 * @return list of all actions
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getActions() {
		List<Action> actionList = actionService.findAll();
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.serializeAllExcept(COMMENTS, EFFMEASCRITERION,
				PROCESSIMPACTS, GAP, CREATEDBY, UPDATEDAT);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter riskFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTION_FILTER, actionFilter)
				.addFilter(USER_FILTER, userFilter).addFilter(RISK_FILTER, riskFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue actionsMapping = new MappingJacksonValue(actionList);
		actionsMapping.setFilters(filters);
		return actionsMapping;
	}

	/**
	 * Gets the list of all actions by page
	 * 
	 * @param pageable
	 *            pagination information
	 * @return list of all actions by page
	 */
	@GetMapping
	public MappingJacksonValue getActionsByPage(Pageable pageable) {
		Page<Action> actionList = actionService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.serializeAllExcept(COMMENTS, EFFMEASCRITERION,
				PROCESSIMPACTS, GAP, CREATEDBY, CREATEDAT, UPDATEDAT);
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter riskFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTION_FILTER, actionFilter)
				.addFilter(USER_FILTER, userFilter).addFilter(RISK_FILTER, riskFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue actionsMapping = new MappingJacksonValue(actionList);
		actionsMapping.setFilters(filters);
		return actionsMapping;
	}

	/**
	 * Gets the list of all actions by there gap
	 * 
	 * @param pageable
	 *            pagination information
	 * @return list of all actions by page
	 */
	@GetMapping(value = "/gap/{id}")
	public MappingJacksonValue getActionsByEcart(@PathVariable Long id) {
		List<Action> actionList = actionService.findByGap(id);
		// Filter the action object
		SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.serializeAll();
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTION_FILTER, actionFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue actionsMapping = new MappingJacksonValue(actionList);
		actionsMapping.setFilters(filters);
		return actionsMapping;
	}

	/**
	 * Gets the list of all actions of one responsible
	 * 
	 * @param username
	 *            userName of the responsible
	 * @param pageable
	 *            pagination information
	 * @return list of all actions of the responsible
	 */
	@GetMapping(value = "/responsible/{username}")
	public MappingJacksonValue getActionsByResponsableAction(@PathVariable String username, Pageable pageable) {
		Page<Action> actionList = actionService.findByResponsibleAction(username, pageable);
		// Filter the action object
		SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter riskFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTION_FILTER, actionFilter)
				.addFilter(RISK_FILTER, riskFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue actionsMapping = new MappingJacksonValue(actionList);
		actionsMapping.setFilters(filters);
		return actionsMapping;
	}

	/**
	 * gets one action by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id
	 *            the id of the action
	 * @return ResponseEntity: the object or the error to display when getting
	 *         action by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getActionById(@PathVariable Long id) {
		try {
			// Set the response with action object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(actionService.findById(id));
			/** Filtering data to send **/
			// Filter the action object
			SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.serializeAll();
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
			SimpleBeanPropertyFilter gapFilter = SimpleBeanPropertyFilter.serializeAllExcept(JUSTIFICATION,
					IMPROVEMENTCLUE, IDENTIFIEDCAUSES, ACTIONS, AUDITREPORT);
			SimpleBeanPropertyFilter riskFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID);
			SimpleBeanPropertyFilter processFilter = SimpleBeanPropertyFilter.serializeAll();
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(ACTION_FILTER, actionFilter)
					.addFilter(USER_FILTER, userFilter).addFilter(GAP_FILTER, gapFilter)
					.addFilter(PROCESS_FILTER, processFilter).addFilter(RISK_FILTER, riskFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue actionMapping = new MappingJacksonValue(objectResponse);
			actionMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(actionMapping);
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
	 * Creates a new action
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param action
	 *            the action to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         action with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createAction(@RequestBody Action action) {
		try {
			// Get connected user
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			User user = userService.findByUsername(currentPrincipalName).get();
			// add connected user to comment
			action.setCreatedBy(user);
			actionService.create(action);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ACTION_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one action
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param action
	 *            the new action object with the new values
	 * @param id
	 *            the id of the action
	 * @return ResponseEntity: the message or the error to display after updating
	 *         action with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateAction(@RequestBody Action action,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_NOT_EXIST));
			System.err.println("position 1");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			actionService.update(action, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ACTION_UPDATED));
			System.err.println("position 2");
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_NOT_EXIST));
			System.err.println("position 3" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			System.err.println("errrrrrrrr" + e.getMessage());
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one action
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this action is used and any other exception
	 * 
	 * @param id
	 *            the of the deleted action
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         action with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteAction(@PathVariable(value = "id") Long id) {
		try {
			actionService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ACTION_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ACTION_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for actions by one term
	 * 
	 * @param term
	 * @param pageable
	 *            pagination information
	 * @return term the term to base search on it
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchActions(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Action> actionList = actionService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.serializeAll();
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ACTION_FILTER, actionFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue actionMapping = new MappingJacksonValue(actionList);
		actionMapping.setFilters(filters);
		return actionMapping;
	}

	/**
	 * Searches for actions by multiple terms
	 *
	 * @param actionSearch
	 *            actionFilter object with all terms for search
	 * @param pagination
	 *            information
	 * @return list of actions contains the input terms by page
	 */
	@PostMapping(value = "/advanced-search")
	public MappingJacksonValue advancedSearch(@Valid @RequestBody ActionFilter actionSearch, Pageable pageable) {
		Page<Action> actionList = actionService.advancedSearch(actionSearch, pageable);
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.serializeAll();
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter).addFilter(ACTION_FILTER,
				actionFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue actionMapping = new MappingJacksonValue(actionList);
		actionMapping.setFilters(filters);
		return actionMapping;
	}

	/**
	 * Add a new comment to action
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param id
	 *            the id of the action
	 * @param comment
	 *            the comment to add
	 * @return ResponseEntity: the message or the error to display after creating
	 *         action with HttpStatus status code
	 */
	@PutMapping(value = "/comments/{id}")
	public ResponseEntity<GenericResponse<String>> addComment(@PathVariable(value = "id") Long id,
			@RequestBody Comment comment) {
		try {
			// Get connected user
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			User user = userService.findByUsername(currentPrincipalName).get();
			// add connected user to comment
			comment.setCreatedBy(user);
			actionService.addComment(id, comment);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(COMMENT_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(COMMENT_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(COMMENT_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one comment from action
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this action is used and any other exception
	 * 
	 * @param id
	 *            the of the action
	 * @param comment
	 *            the comment to delete
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         action with HttpStatus status code
	 */
	@DeleteMapping(value = "/comments/{id}")
	public ResponseEntity<GenericResponse<String>> deleteComment(@PathVariable(value = "id") Long id,
			@RequestBody Comment comment) {
		try {

			actionService.deleteComment(id, comment);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(COMMENT_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(COMMENT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(COMMENT_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}
}
