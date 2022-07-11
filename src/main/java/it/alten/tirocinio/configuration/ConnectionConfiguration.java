package it.alten.tirocinio.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class ConnectionConfiguration {
	@Value("${spring.datasource.url}")
	private String databaseUrl;
	
	@Value("${spring.datasource.username}")
	private String databaseUserName;
	
	@Value("${spring.datasource.password}")
	private String databasePassword;
	
	@Bean
	@SessionScope
	public Connection connection() {
		try {
			return DriverManager.getConnection(databaseUrl, databaseUserName, databasePassword);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
