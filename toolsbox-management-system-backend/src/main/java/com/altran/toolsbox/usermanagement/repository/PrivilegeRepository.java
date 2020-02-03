package com.altran.toolsbox.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.usermanagement.model.Privilege;

/**
 * Represents repository of privilege
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
