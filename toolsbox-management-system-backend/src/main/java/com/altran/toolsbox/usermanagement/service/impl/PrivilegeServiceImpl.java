package com.altran.toolsbox.usermanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.usermanagement.model.Privilege;
import com.altran.toolsbox.usermanagement.repository.PrivilegeRepository;
import com.altran.toolsbox.usermanagement.service.PrivilegeService;

/**
 * Represents implementation of privilege service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	private PrivilegeRepository privilegeRepository;

	@Autowired
	public void setPrivilegeRepository(PrivilegeRepository privilegeRepository) {
		this.privilegeRepository = privilegeRepository;
	}

	@Override
	public List<Privilege> findAll() {
		return privilegeRepository.findAll();
	}

	@Override
	public Privilege findById(Long id) {
		return privilegeRepository.findById(id).orElse(null);
	}

}
