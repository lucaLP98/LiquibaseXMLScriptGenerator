package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropUniqueConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropNotNullConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddNotNullConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddUniqueConstraintScriptDTO;
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
	 * Method for generate an Add Column Script
	 */
	String generateAddColumnLiquibaseXMLScript(AddColumnScriptDTO addColumnScriptDTO);
	
	/*
	 * Method for generate a Drop Not Null Constraint Script
	 */
	String generateDropNotNullConstraintLiquibaseXMLScript(DropNotNullConstraintScriptDTO dropNotNullConstraintScriptDTO);
	
	/*
	 * Method for generate an Add Not Null Constraint Script
	 */
	String generateAddNotNullConstraintLiquibaseXMLScript(AddNotNullConstraintScriptDTO addNotNullConstraintScriptDTO);

	/*
	 * Method for generate an Add Unique Constraint Script
	 */
	String generateAddUniqueConstraintLiquibaseXMLScript(AddUniqueConstraintScriptDTO addUniqueConstraintScriptDTO);
	
	/*
	 * Method for generate an Drop Unique Constraint Script
	 */
	String generateDropUniqueConstraintLiquibaseXMLScript(DropUniqueConstraintScriptDTO dropUniqueConstraintScriptDTO);
	
	/*
	 * Method for generate an Rename Table Script
	 */
	String generateRenameTableLiquibaseXMLScript(RenameTableScriptDTO renameTableScriptDTO);
	
	/*
	 * Method for generate an Rename Table Script
	 */
	String generateRenameColumnLiquibaseXMLScript(RenameColumnScriptDTO renameColumnScriptDTO);
}
