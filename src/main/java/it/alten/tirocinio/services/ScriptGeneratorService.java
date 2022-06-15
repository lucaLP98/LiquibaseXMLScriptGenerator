package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateSchemaScriptDTO;

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
	
	/*
	 * Method for generate a Create Table Script
	 */
	String generateCreateTableLiquibaseXMLScript(CreateTableScriptDTO createTableScriptDTO);
	
	/*
	 * Method for generate a Create Schema Script
	 */
	String generateCreateSchemaLiquibaseXMLScript(CreateSchemaScriptDTO createSchemaScriptDTO);
	
	/*
	 * Method for generate a Add Column Script
	 */
	String generateAddColumnLiquibaseXMLScript(AddColumnScriptDTO addColumnScriptDTO);
}
