package com.altran.toolsbox.usermanagement.model;

import java.io.Serializable;

public class LoginUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
