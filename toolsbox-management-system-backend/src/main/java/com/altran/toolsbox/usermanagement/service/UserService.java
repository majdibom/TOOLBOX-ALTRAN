package com.altran.toolsbox.usermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents the interface of user service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface UserService {

	List<User> findAll();

	Page<User> findAllByPage(Authentication auth, Pageable pageable);

	User findById(Long id);

	Optional<User> findByUsername(String username);

	User getAuthenticatedUser(Authentication auth);

	List<User> getAuditors();

	List<String> findAllEmails();

	User create(User user);

	User update(User user, Long id);

	void delete(Long id);

	User changeAccountState(Long id);

}
