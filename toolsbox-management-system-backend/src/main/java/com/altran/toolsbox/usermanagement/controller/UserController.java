package com.altran.toolsbox.usermanagement.controller;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.service.UserService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import static com.altran.toolsbox.util.constant.ColumnConstants.*;
import static com.altran.toolsbox.util.constant.ResponseConstants.*;
import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.ROLE_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTIVITY_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.AUDIT_FILTER;
import static com.altran.toolsbox.util.constant.FilterConstants.ACTION_FILTER;

/**
 * Represents Rest controller of user
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

	private final UserService userService;

	private GenericResponse<User> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of UserController
	 * 
	 * @param userService
	 *            the service of user
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse
	 *            generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<User> objectResponse) {
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
	 * 
	 * Gets the list of all users
	 * 
	 * @return list of all users
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getUsers() {
		List<User> userList = userService.findAll();
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(userList);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets the list of all users by page
	 * 
	 * @param pageable
	 *            pagination information
	 * @return list of all users by page
	 */
	@GetMapping
	public MappingJacksonValue getUsersByPage(Authentication auth, Pageable pageable) {
		Page<User> userList = userService.findAllByPage(auth, pageable);
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVATED, ROLES, ACTIVITY, ROLES);
		// Filter the role of the user object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
				.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(userList);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets authenticated user
	 * 
	 * @param Authentication
	 *            object
	 * @return authenticated user
	 */
	@GetMapping(value = "/auth")
	public MappingJacksonValue getAuth(Authentication auth) {
		User authUser = userService.getAuthenticatedUser(auth);
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVITY, USERNAME, ACTIVATED, ROLES);
		// Filter the role of the user object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, PRIVILEGES);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
				.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(authUser);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets the list of auditors
	 * 
	 * @return list of all auditors
	 */
	@GetMapping(value = "/auditors")
	public MappingJacksonValue getAuditeurs() {
		List<User> userList = userService.getAuditors();
		/** Filtering data to send **/
		// Filter the action object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(userList);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets one user by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id
	 *            the id of the user
	 * @return ResponseEntity: the object or the error to display when getting user
	 *         by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with user object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(userService.findById(id));
			/** Filtering data to send **/
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.serializeAll();
			// Filter the role of the user object
			SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the audit object
			SimpleBeanPropertyFilter auditFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, RISKS);
			// Filter the audit object
			SimpleBeanPropertyFilter actionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
					.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter)
					.addFilter(AUDIT_FILTER, auditFilter).addFilter(ACTION_FILTER, actionFilter);
			;
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue userMapping = new MappingJacksonValue(objectResponse);
			userMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(userMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Gets one user by his userName
	 * 
	 * Handles UsernameNotFoundException if there is no user with such userName and
	 * any other exception
	 * 
	 * @param userName
	 *            the userName of the user
	 * @return ResponseEntity: the object or the error to display when getting user
	 *         by userName with HttpStatus status code
	 */
	@GetMapping(value = "/username/{userName}")
	public ResponseEntity<Object> getUserByUserName(@PathVariable(value = "userName") String userName) {
		try {
			// Set the response with user object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(userService.findByUsername(userName).get());
			/** Filtering data to send **/
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
					EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVITY, USERNAME, ACTIVATED, ROLES);
			// Filter the role of the user object
			SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, "privileges");
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
					.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue userMapping = new MappingJacksonValue(objectResponse);
			userMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(userMapping);
		} catch (UsernameNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new user
	 * 
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID, AddressException if sender's address mail is not correct,
	 * MessagingException, IOException and any other exception
	 * 
	 * @param user
	 *            the user to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         user with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createUser(@RequestBody User user) {
		try {
			userService.create(user);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(USER_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_WRONG_MAIL_ADDRESS));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one user
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param user
	 *            the new user object with the new values
	 * @param id
	 *            the id of the updated user
	 * @return ResponseEntity: the message or the error to display after updating
	 *         user with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateUser(@RequestBody @Valid User user,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			userService.update(user, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(USER_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one user
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this user is used and any other exception
	 * 
	 * @param id
	 *            the of the deleted user
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         user with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteUser(@PathVariable(value = "id") Long id) {
		try {
			userService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(USER_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Changes account state of a user
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param id
	 *            the id of the updated user
	 * @return ResponseEntity: the object or the error to display after updating
	 *         user with HttpStatus status code
	 */
	@PutMapping(value = "/{id}/account")
	public ResponseEntity<GenericResponse<String>> changeAccountState(@PathVariable(value = "id") Long id) {
		try {
			User updatedUser = userService.changeAccountState(id);
			messageResponse.setError(false);
			if (updatedUser.isActivated())
				messageResponse.setValue(Translator.toLocale(ACTIVATE_ACCOUNT));
			else
				messageResponse.setValue(Translator.toLocale(DISABLE_ACCOUNT));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

}
