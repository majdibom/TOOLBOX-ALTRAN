package com.altran.toolsbox.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.usermanagement.model.LoginUser;
import com.altran.toolsbox.usermanagement.repository.UserRepository;
import com.altran.toolsbox.usermanagement.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static com.altran.toolsbox.util.constant.ResponseConstants.ERROR;
import static com.altran.toolsbox.util.constant.ResponseConstants.MESSAGE;

@RestController
@RequestMapping
public class AuthenticationController {

	private AuthenticationManager authenticationManager;

	private JwtTokenProvider jwtTokenProvider;

	private UserRepository userRepository;

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Autowired
	public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Autowired
	public void setUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Verifies if user exists, active and verifies if userName and password are
	 * correct
	 * 
	 * @param loginUser
	 * @return ResponseEntity: the object or the error to display when
	 *         authenticating
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> signin(@RequestBody LoginUser data) {
		try {
			String username = data.getUsername();
			String password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			String token = jwtTokenProvider.createToken(username,
					userRepository.findByUsername(username)
							.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
							.getRoles().stream()
							.map(role -> role.getPrivileges().stream().map(privilege -> privilege.getTitle())
									.collect(Collectors.toSet()))
							.collect(Collectors.toList()),
					userRepository.findByUsername(username)
							.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
							.getRoles().stream().map(role -> role.getTitle()).collect(Collectors.toList()));
			Map<String, Object> response = new HashMap<>();
			response.put(ERROR, false);
			response.put(MESSAGE, token);
			return ok(response);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}
