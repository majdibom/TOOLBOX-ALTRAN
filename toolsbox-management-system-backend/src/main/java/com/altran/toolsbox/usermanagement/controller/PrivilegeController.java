package com.altran.toolsbox.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.usermanagement.model.Privilege;
import com.altran.toolsbox.usermanagement.service.PrivilegeService;

/**
 * Represents Rest controller of privilege
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/privileges")
public class PrivilegeController {

	private final PrivilegeService privilegeService;

	/**
	 * Constructor of PrivilegesController
	 * 
	 * @param privilegeService the service of privilege
	 */
	@Autowired
	public PrivilegeController(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	/**
	 * Gets the list of all privileges
	 * 
	 * @return list of all privileges
	 */
	@GetMapping
	public List<Privilege> getPrivileges() {
		return privilegeService.findAll();
	}

}
