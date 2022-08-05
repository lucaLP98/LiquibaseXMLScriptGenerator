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
	String generateDropTableLiquibaseXMLScript(DropTableScriptDTO dropTableScriptDTO, boolean indentation);
	
	/*
	 * Method for generate a Drop Column Script
	 */
	String generateDropColumnLiquibaseXMLScript(DropColumnScriptDTO dropColumnScriptDTO, boolean indentation);
	
	/*
	 * Method for generate a Create Table Script
	 */
	String generateCreateTableLiquibaseXMLScript(CreateTableScriptDTO createTableScriptDTO, boolean indentation);
	
	/*
	 * Method for generate a Create SchemaMetadata Script
	 */
	String generateCreateSchemaLiquibaseXMLScript(CreateSchemaScriptDTO createSchemaScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Add Column Script
	 */
	String generateAddColumnLiquibaseXMLScript(AddColumnScriptDTO addColumnScriptDTO, boolean indentation);
	
	/*
	 * Method for generate a Drop Not Null Constraint Script
	 */
	String generateDropNotNullConstraintLiquibaseXMLScript(DropNotNullConstraintScriptDTO dropNotNullConstraintScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Add Not Null Constraint Script
	 */
	String generateAddNotNullConstraintLiquibaseXMLScript(AddNotNullConstraintScriptDTO addNotNullConstraintScriptDTO, boolean indentation);

	/*
	 * Method for generate an Add Unique Constraint Script
	 */
	String generateAddUniqueConstraintLiquibaseXMLScript(AddUniqueConstraintScriptDTO addUniqueConstraintScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Drop Unique Constraint Script
	 */
	String generateDropUniqueConstraintLiquibaseXMLScript(DropUniqueConstraintScriptDTO dropUniqueConstraintScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Rename Table Script
	 */
	String generateRenameTableLiquibaseXMLScript(RenameTableScriptDTO renameTableScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Rename Column Script
	 */
	String generateRenameColumnLiquibaseXMLScript(RenameColumnScriptDTO renameColumnScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Modify Column Data Type Script
	 */
	String generateModifyColumnDataTypeLiquibaseXMLScript(ModifyColumnDataTypeScriptDTO modifyColumnDataTypeScriptDTO, boolean indentation);

	/*
	 * Method for generate an Add Auto Increment Script
	 */
	String generateAddAutoIncrementLiquibaseXMLScript(AddAutoIncrementScriptDTO addAutoIncrementScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Add Default Value Script
	 */
	String generateAddDefaultValueLiquibaseXMLScript(AddDefaultValueScriptDTO addDefaultValueScriptDTO, boolean indentation);
	
	/*
	 * Method for generate a Drop Default Value Script
	 */
	String generateDropDefaultValueLiquibaseXMLScript(DropDefaultValueScriptDTO dropDefaultValueScriptDTO, boolean indentation);
	
	/*
	 * Method for generate an Add Foreign Key Constraint Script
	 */
	String generateAddForeignKeyConstraintLiquibaseXMLScript(AddForeignKeyConstraintScriptDTO addForeignKeyConstraintScriptDTO, boolean indentation);

	/*
	 * Method for generate a Drop Foreign Key Constraint Script
	 */
	String generateDropForeignKeyConstraintLiquibaseXMLScript(DropForeignKeyConstraintScriptDTO dropForeignKeyConstraintScriptDTO, boolean indentation);

	/*
	 * Method for generate a Delete Query Script
	 */
	String generateDeleteDataLiquibaseXMLScript(DeleteDataScriptDTO deleteDataScriptDTO, boolean indentation);
	
	/*
	 * Method for generate a Insert Query Script
	 */
	String generateInsertDataLiquibaseXMLScript(InsertDataScriptDTO insertDataScriptDTO, boolean indentation);
	
	/*
	 * Method for generate a Update Query Script
	 */
	String generateUpdateDataLiquibaseXMLScript(UpdateDataScriptDTO updateDataScriptDTO, boolean indentation);
}
