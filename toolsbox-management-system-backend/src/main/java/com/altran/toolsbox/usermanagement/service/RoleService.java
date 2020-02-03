package com.altran.toolsbox.usermanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.usermanagement.model.Role;

/**
 * Represents the interface of role service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
public interface RoleService {

	List<Role> findAll();

	Page<Role> findAllByPage(Pageable pageable);

	Role findById(Long id);

	Role findByTitle(String title);

	List<String> findAllTitles();

	Page<Role> simpleSearch(String term, Pageable pageable);

	Role create(Role role);

	Role update(Role role, Long id);

	void delete(Long id);

}
