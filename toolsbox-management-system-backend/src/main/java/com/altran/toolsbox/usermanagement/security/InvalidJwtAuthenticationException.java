package com.altran.toolsbox.usermanagement.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidJwtAuthenticationException(String e) {
		super(e);
	}
}