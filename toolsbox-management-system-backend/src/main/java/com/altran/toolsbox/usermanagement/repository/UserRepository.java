package com.altran.toolsbox.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.usermanagement.model.Role;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of user
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByOrderByFirstNameAsc();

	Optional<User> findByUsername(String username);

	List<User> findByRoles(Role role);

}
