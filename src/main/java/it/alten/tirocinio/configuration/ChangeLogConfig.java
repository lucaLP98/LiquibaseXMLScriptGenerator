package it.alten.tirocinio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;

@Configuration
public class ChangeLogConfig {
	
	@Bean
	@SessionScope
	public ChangeLog sessionChangeLog() {
		return new ChangeLog();
	}
}
