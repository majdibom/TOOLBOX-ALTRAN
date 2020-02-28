package com.altran.toolsbox.usermanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.usermanagement.model.Role;
import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.repository.UserRepository;
import com.altran.toolsbox.usermanagement.service.RoleService;
import com.altran.toolsbox.usermanagement.service.UserService;

/**
 * Represents implementation of user service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;

	private RoleService roleService;

	private BCryptPasswordEncoder bcryptEncoder;

	/**
	 * Constructor of UserServiceImpl
	 * 
	 * @param userRepository
	 *            the repository of user
	 * 
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Changes role service.
	 * 
	 * @param roleService
	 *            role service.
	 */
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * Changes bcryptEncoder.
	 * 
	 * @param bcryptEncoder
	 *            BCryptPasswordEncoder object.
	 */
	@Autowired
	public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
		this.bcryptEncoder = bcryptEncoder;
	}

	/**
	 * Gets the list of all users sorted by firstName
	 * 
	 * @return list of all users sorted by firstName
	 */
	public List<User> findAll() {
		return userRepository.findAllByOrderByFirstNameAsc();
	}

	/**
	 * Gets the list of all users by page and sorted by firstName
	 * 
	 * @return list of all users by page and sorted by firstName
	 */
	public Page<User> findAllByPage(Authentication auth, Pageable pageable) {
		// return list of all users
		List<User> users = findAll();
		// gets authenticated user
		User authenticatedUser = getAuthenticatedUser(auth);
		// remove authenticated user from list of users
		List<User> result = users.stream().filter(user -> (authenticatedUser != user))
				.collect(Collectors.toCollection(ArrayList::new));
		// convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<User> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());
	}

	/**
	 * Gets the list of all auditors
	 * 
	 * @return list of all auditors
	 */
	@Override
	public List<User> getAuditors() {
		Role role = roleService.findByTitle("Auditeur");
		return userRepository.findByRoles(role);
	}

	/**
	 * Gets one user by his userName
	 * 
	 * @param userName
	 *            the userName of the user
	 * @return user object with the same userName
	 * @throws UsernameNotFoundException
	 *             if there is no user with such userName
	 */
	@Override
	public Optional<User> findByUsername(String username) {
		System.err.println("findByUsername");

		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent())
			return user;
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Gets one user by his id
	 * 
	 * @param id
	 *            the id of the user
	 * @return user object with the same id
	 * @throws NoSuchElementException
	 *             if no element is present with such ID
	 */
	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new user
	 * 
	 * @param user
	 *            the user to create
	 * @return the created user
	 * @throws IOException
	 * @throws MessagingException
	 * @throws AddressException
	 * @throws EntityExistsException
	 *             if there is already existing entity with such ID
	 */
	@Override
	public User create(User user) {

		if (user.getId() != null && userRepository.existsById(user.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		// generate password automatically
		user.setUsername(user.getFirstName());
		user.setPassword(user.getFirstName());
		// encrypt password
		String oldpsw = user.getPassword();
		String password = bcryptEncoder.encode(oldpsw);
		// Encode password
		user.setPassword(password);
		// set activated to true when the user created
		user.setActivated(true);
		// send email to user while creating him
		return userRepository.save(user);
	}

	/**
	 * Updates one user
	 * 
	 * @param id
	 *            the id of the updated user
	 * @param user
	 *            the new user object with the new values
	 * @return the updated user
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID in the database
	 */
	@Override
	public User update(User user, Long id) {
		if (id != null && !userRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		user.setId(id);
		// Get the password from the old user object
		user.setPassword(findById(id).getPassword());
		return userRepository.save(user);
	}

	/**
	 * Deletes one user
	 * 
	 * @param id
	 *            the id of the deleted user
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !userRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		userRepository.deleteById(id);
	}

	/**
	 * Changes account state of a user
	 * 
	 * @param id
	 *            the of the updated user
	 * @return the updated user
	 */
	@Override
	public User changeAccountState(Long id) {
		User user = findById(id);
		if (user.isActivated())
			user.setActivated(false);
		else
			user.setActivated(true);
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user));
	}

	private Set<GrantedAuthority> getAuthority(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> role.getPrivileges()
				.forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getTitle()))));
		return authorities;
	}

	/**
	 * Gets authenticated user
	 * 
	 * @param Authentication
	 *            object
	 * @return authenticated user
	 */
	public User getAuthenticatedUser(Authentication auth) {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) auth
				.getPrincipal();
		return findByUsername(principal.getUsername()).get();
	}

	/**
	 * Gets the list of from all users
	 *
	 * @return list of mails
	 */
	@Override
	public List<String> findAllEmails() {
		List<User> users = userRepository.findAll();
		List<String> emails = new ArrayList<>();
		if (!(users.isEmpty())) {
			for (User user : users) {
				emails.add(user.getEmail());
			}
		}
		return emails;
	}

}
