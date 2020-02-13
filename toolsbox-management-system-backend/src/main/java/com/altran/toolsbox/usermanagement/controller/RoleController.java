package com.altran.toolsbox.usermanagement.controller;

import static com.altran.toolsbox.util.constant.ColumnConstants.*;
import static com.altran.toolsbox.util.constant.ResponseConstants.*;
import static com.altran.toolsbox.util.constant.FilterConstants.ROLE_FILTER;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.usermanagement.model.Role;
import com.altran.toolsbox.usermanagement.service.RoleService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of role
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/roles")
public class RoleController {

	private final RoleService roleService;

	private GenericResponse<Role> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of RoleController
	 * 
	 * @param roleService the service of role
	 */
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Role> objectResponse) {
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
	 * Gets the list of all roles
	 * 
	 * @return list of all roles
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getRoles() {
		List<Role> roleList = roleService.findAll();
		/** Filtering data to send **/
		// Filter the role object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ROLE_FILTER, roleFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue roleMapping = new MappingJacksonValue(roleList);
		roleMapping.setFilters(filters);
		return roleMapping;
	}

	/**
	 * Gets the list of all roles by page
	 * 
	 * @param pageable pagination information
	 * @return list of all roles by page
	 */
	@GetMapping
	public MappingJacksonValue getRolesByPage(Pageable pageable) {
		Page<Role> roleList = roleService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the role object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ROLE_FILTER, roleFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue roleMapping = new MappingJacksonValue(roleList);
		roleMapping.setFilters(filters);
		return roleMapping;
	}

	/**
	 * Gets the list of titles of roles
	 * 
	 * @return list of titles of roles
	 */
	@GetMapping(value = "/titles")
	public List<String> findAllTitles() {
		return roleService.findAllTitles();
	}

	/**
	 * Gets one role by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the role
	 * @return ResponseEntity: the object or the error to display when getting role
	 *         by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getRoleById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with role object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(roleService.findById(id));
			/** Filtering data to send **/
			SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.serializeAll();
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(ROLE_FILTER, roleFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue roleMapping = new MappingJacksonValue(objectResponse);
			roleMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(roleMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new role
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param role the role to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         role with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createRole(@RequestBody @Valid Role role) {
		try {
			roleService.create(role);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ROLE_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one role
	 * 
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param role the new role object with the new values
	 * @param the  id of the role
	 * @return ResponseEntity: the message or the error to display after updating
	 *         role with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateRole(@RequestBody @Valid Role role,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			roleService.update(role, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ROLE_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one role
	 * 
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this role is used and any other exception
	 * 
	 * @param id the of the deleted role
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         role with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteRole(@PathVariable(value = "id") Long id) {
		try {
			roleService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ROLE_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ROLE_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for roles by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of roles contains the input term by page
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchRoles(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Role> roleList = roleService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the role object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ROLE_FILTER, roleFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue roleMapping = new MappingJacksonValue(roleList);
		roleMapping.setFilters(filters);
		return roleMapping;
	}

}
