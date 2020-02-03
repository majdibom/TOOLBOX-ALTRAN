package com.altran.toolsbox.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.usermanagement.model.Role;
import com.altran.toolsbox.usermanagement.repository.RoleRepository;
import com.altran.toolsbox.usermanagement.service.RoleService;

import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

/**
 * Represents implementation of role service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	/**
	 * Constructor of RoleServiceImpl
	 * 
	 * @param roleRepository the repository of role
	 */
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	/**
	 * Gets the list of all roles sorted by title
	 * 
	 * @return list of all roles sorted by title
	 */
	@Override
	public List<Role> findAll() {
		return roleRepository.findAllByOrderByTitleAsc();
	}

	/**
	 * Gets the list of all roles by page and sorted by title
	 * 
	 * @param pageable pagination information
	 * @return list of all roles by page and sorted by title
	 */
	public Page<Role> findAllByPage(Pageable pageable) {
		return roleRepository.findAllByOrderByTitleAsc(pageable);
	}

	/**
	 * Gets the list of titles of roles
	 * 
	 * @return list of titles of roles
	 */
	@Override
	public List<String> findAllTitles() {
		// lambda expression
		return findAll().stream().map(Role::getTitle).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Gets one role by his id
	 * 
	 * @param id the id of the role
	 * @return role object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Role findById(Long id) {
		Optional<Role> role = roleRepository.findById(id);
		if (role.isPresent())
			return role.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Gets one role by his title
	 * 
	 * @param name the title of the role
	 * @return role object with the same name
	 * @throws NoSuchElementException if no element is present with such Title
	 */
	@Override
	public Role findByTitle(String title) {
		Optional<Role> role = roleRepository.findByTitle(title);
		if (role.isPresent())
			return role.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new role
	 * 
	 * @param role the role to create
	 * @return the created role
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Role create(Role role) {
		if (role.getId() != null && roleRepository.existsById(role.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		return roleRepository.save(role);
	}

	/**
	 * Updates one role
	 * 
	 * @param id   the id of the role
	 * @param role the new role object with the new values
	 * @return the updated role
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Role update(Role role, Long id) {
		if (id != null && !roleRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		role.setId(id);
		return roleRepository.save(role);
	}

	/**
	 * Deletes one role
	 * 
	 * @param id the of the deleted role
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !roleRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		roleRepository.deleteById(id);
	}

	/**
	 * Searches for roles by one term
	 * 
	 * @param pageable pagination information
	 * @param term     the term to base search on it
	 * @return list of roles contains the input term by page
	 */
	@Override
	public Page<Role> simpleSearch(String term, Pageable pageable) {
		return roleRepository.simpleSearch(term, pageable);
	}

}
