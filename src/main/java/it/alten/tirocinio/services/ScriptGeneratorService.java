package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropUniqueConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.InsertDataScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.ModifyColumnDataTypeScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.UpdateDataScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropDefaultValueScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropForeignKeyConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropNotNullConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DeleteDataScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddAutoIncrementScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddDefaultValueScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddForeignKeyConstraintScriptDTO;
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
	 * Method for generate an Rename Column Script
	 */
	String generateRenameColumnLiquibaseXMLScript(RenameColumnScriptDTO renameColumnScriptDTO);
	
	/*
	 * Method for generate an Modify Column Data Type Script
	 */
	String generateModifyColumnDataTypeLiquibaseXMLScript(ModifyColumnDataTypeScriptDTO modifyColumnDataTypeScriptDTO);

	/*
	 * Method for generate an Add Auto Increment Script
	 */
	String generateAddAutoIncrementLiquibaseXMLScript(AddAutoIncrementScriptDTO addAutoIncrementScriptDTO);
	
	/*
	 * Method for generate an Add Default Value Script
	 */
	String generateAddDefaultValueLiquibaseXMLScript(AddDefaultValueScriptDTO addDefaultValueScriptDTO);
	
	/*
	 * Method for generate a Drop Default Value Script
	 */
	String generateDropDefaultValueLiquibaseXMLScript(DropDefaultValueScriptDTO dropDefaultValueScriptDTO);
	
	/*
	 * Method for generate an Add Foreign Key Constraint Script
	 */
	String generateAddForeignKeyConstraintLiquibaseXMLScript(AddForeignKeyConstraintScriptDTO addForeignKeyConstraintScriptDTO);

	/*
	 * Method for generate a Drop Foreign Key Constraint Script
	 */
	String generateDropForeignKeyConstraintLiquibaseXMLScript(DropForeignKeyConstraintScriptDTO dropForeignKeyConstraintScriptDTO);

	/*
	 * Method for generate a Delete Query Script
	 */
	String generateDeleteDataLiquibaseXMLScript(DeleteDataScriptDTO deleteDataScriptDTO);
	
	/*
	 * Method for generate a Insert Query Script
	 */
	String generateInsertDataLiquibaseXMLScript(InsertDataScriptDTO insertDataScriptDTO);
	
	/*
	 * Method for generate a Update Query Script
	 */
	String generateUpdateDataLiquibaseXMLScript(UpdateDataScriptDTO updateDataScriptDTO);
}
