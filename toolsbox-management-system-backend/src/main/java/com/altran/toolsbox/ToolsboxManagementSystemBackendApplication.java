package com.altran.toolsbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ToolsboxManagementSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolsboxManagementSystemBackendApplication.class, args);
	}

}
