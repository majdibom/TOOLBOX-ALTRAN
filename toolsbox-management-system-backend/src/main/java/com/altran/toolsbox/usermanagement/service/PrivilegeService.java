package com.altran.toolsbox.usermanagement.service;

import java.util.List;

import com.altran.toolsbox.usermanagement.model.Privilege;

/**
 * Represents the interface of privilege service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
public interface PrivilegeService {

	List<Privilege> findAll();

	Privilege findById(Long id);

}
