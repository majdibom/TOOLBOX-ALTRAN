package com.altran.toolsbox.usermanagement.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

	List<String> getPrivileges(UserDetails userDetails);
}
