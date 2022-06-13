package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;

/*
 * Service that offers methods for generate different types of Liquibase Script
 */
public interface ScriptGeneratorService {
	/*
	 * Method for generate a Drop Table Script
	 */
	String generateDropTableLiquibaseXMLScript(DropTableScriptDTO dropTableScriptDTO);
	
	/*
	 * Method for generate a Drop Column Script
	 */
	String generateDropColumnLiquibaseXMLScript(DropColumnScriptDTO dropColumnScriptDTO);
}
