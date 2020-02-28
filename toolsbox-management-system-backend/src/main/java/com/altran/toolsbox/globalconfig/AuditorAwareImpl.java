package com.altran.toolsbox.globalconfig;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.altran.toolsbox.usermanagement.model.User;

public class AuditorAwareImpl implements AuditorAware<Long> {

	// @Autowired
	// private UserService userService;

	@Override
	public Optional<Long> getCurrentAuditor() {

		Long userId = 0L;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication() != null) {
			User user = (User) securityContext.getAuthentication().getPrincipal();
			userId = Long.valueOf(user.getId());
		}
		Optional<Long> id = Optional.of(userId);
		return id;
	}

}