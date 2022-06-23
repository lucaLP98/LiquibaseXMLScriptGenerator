package it.alten.tirocinio.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

import it.alten.tirocinio.services.ScriptGeneratorService;

/*
 * Spring Controller for operations of script generation
 */
@RestController
@RequestMapping("/api/scriptGenerator/")
public class ScriptGeneratorController {
	private final ScriptGeneratorService scriptGeneratorService;
	
	public ScriptGeneratorController(ScriptGeneratorService scriptGeneratorService) {
		this.scriptGeneratorService = scriptGeneratorService;
	}
	
	/*
	 * POST REQUESTS 
	 */
	
	/*
	 * Method to manage drop table xml script request
	 */
	@PostMapping("/dropTableScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateDropTableScriptRequest(@RequestBody DropTableScriptDTO dropTableScriptDTO) {
		return scriptGeneratorService.generateDropTableLiquibaseXMLScript(dropTableScriptDTO);
	}
	
	/*
	 * Method to manage drop column xml script request
	 */
	@PostMapping("/dropColumnScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateDropColumnScriptRequest(@RequestBody DropColumnScriptDTO dropColumnScriptDTO) {
		return scriptGeneratorService.generateDropColumnLiquibaseXMLScript(dropColumnScriptDTO);
	}
	
	/*
	 * Method to manage create table xml script request
	 */
	@PostMapping("/createTableScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateCreateTableScriptRequest(@RequestBody CreateTableScriptDTO createTableScriptDTO) {
		return scriptGeneratorService.generateCreateTableLiquibaseXMLScript(createTableScriptDTO);
	}
	
	/*
	 * Method to manage create schema xml script request
	 */
	@PostMapping("/createSchemaScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateCreateSchemaScriptRequest(@RequestBody CreateSchemaScriptDTO createSchemaScriptDTO) {
		return scriptGeneratorService.generateCreateSchemaLiquibaseXMLScript(createSchemaScriptDTO);
	}
	
	/*
	 * Method to manage add column xml script request
	 */
	@PostMapping("/addColumnScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateAddColumnScriptRequest(@RequestBody AddColumnScriptDTO addColumnScriptDTO) {
		return scriptGeneratorService.generateAddColumnLiquibaseXMLScript(addColumnScriptDTO);
	}
	
	/*
	 * Method to manage drop not null constraint xml script request
	 */
	@PostMapping("/dropNotNullConstraintScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateDropNotNullConstraintScriptRequest(@RequestBody DropNotNullConstraintScriptDTO dropNotNullConstraintScriptDTO) {
		return scriptGeneratorService.generateDropNotNullConstraintLiquibaseXMLScript(dropNotNullConstraintScriptDTO);
	}
	
	/*
	 * Method to manage add not null constraint xml script request
	 */
	@PostMapping("/addNotNullConstraintScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateAddNotNullConstraintScriptRequest(@RequestBody AddNotNullConstraintScriptDTO addNotNullConstraintScriptDTO) {
		return scriptGeneratorService.generateAddNotNullConstraintLiquibaseXMLScript(addNotNullConstraintScriptDTO);
	}
	
	/*
	 * Method to manage add unique constraint xml script request
	 */
	@PostMapping("/addUniqueConstraintScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateAddUniqueConstraintScriptRequest(@RequestBody AddUniqueConstraintScriptDTO addUniqueConstraintScriptDTO) {
		return scriptGeneratorService.generateAddUniqueConstraintLiquibaseXMLScript(addUniqueConstraintScriptDTO);
	}
	
	/*
	 * Method to manage add unique constraint xml script request
	 */
	@PostMapping("/dropUniqueConstraintScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateDropUniqueConstraintScriptRequest(@RequestBody DropUniqueConstraintScriptDTO dropUniqueConstraintScriptDTO) {
		return scriptGeneratorService.generateDropUniqueConstraintLiquibaseXMLScript(dropUniqueConstraintScriptDTO);
	}
	
	/*
	 * Method to manage rename table xml script request
	 */
	@PostMapping("/renameTableScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateRenameTableScriptRequest(@RequestBody RenameTableScriptDTO renameTableScriptDTO) {
		return scriptGeneratorService.generateRenameTableLiquibaseXMLScript(renameTableScriptDTO);
	}
	
	/*
	 * Method to manage rename column xml script request
	 */
	@PostMapping("/renameColumnScript/")
	@ResponseStatus(HttpStatus.OK)
	public String generateRenameColumnScriptRequest(@RequestBody RenameColumnScriptDTO renameColumnScriptDTO) {
		return scriptGeneratorService.generateRenameColumnLiquibaseXMLScript(renameColumnScriptDTO);
	}
	
	/*
	 * Method to manage Modify Column Data Type Script xml script request
	 */
	@PostMapping("/modifyColumnDataType/")
	@ResponseStatus(HttpStatus.OK)
	public String generateModifyColumnDataTypeScriptRequest(@RequestBody ModifyColumnDataTypeScriptDTO modifyColumnDataTypeScriptDTO) {
		return scriptGeneratorService.generateModifyColumnDataTypeLiquibaseXMLScript(modifyColumnDataTypeScriptDTO);
	}
	
	/*
	 * Method to manage add auto increment Script xml script request
	 */
	@PostMapping("/addAutoIncrement/")
	@ResponseStatus(HttpStatus.OK)
	public String generateAddAutoIncrementScriptRequest(@RequestBody AddAutoIncrementScriptDTO addAutoIncrementScriptDTO) {
		return scriptGeneratorService.generateAddAutoIncrementLiquibaseXMLScript(addAutoIncrementScriptDTO);
	}
	
	/*
	 * Method to manage add default value Script xml script request
	 */
	@PostMapping("/addDefaultValue/")
	@ResponseStatus(HttpStatus.OK)
	public String generateAddDefaultValueScriptRequest(@RequestBody AddDefaultValueScriptDTO addDefaultValueScriptDTO) {
		return scriptGeneratorService.generateAddDefaultValueLiquibaseXMLScript(addDefaultValueScriptDTO);
	}
	
	/*
	 * Method to manage drop default value Script xml script request
	 */
	@PostMapping("/dropDefaultValue/")
	@ResponseStatus(HttpStatus.OK)
	public String generateDropDefaultValueScriptRequest(@RequestBody DropDefaultValueScriptDTO dropDefaultValueScriptDTO) {
		return scriptGeneratorService.generateDropDefaultValueLiquibaseXMLScript(dropDefaultValueScriptDTO);
	}
	
	/*
	 * Method to manage add Foreign Key Constraint Script xml script request
	 */
	@PostMapping("/addForeignKeyConstraint/")
	@ResponseStatus(HttpStatus.OK)
	public String generateAddForeignKeyConstraintScriptRequest(@RequestBody AddForeignKeyConstraintScriptDTO addForeignKeyConstraintScriptDTO) {
		return scriptGeneratorService.generateAddForeignKeyConstraintLiquibaseXMLScript(addForeignKeyConstraintScriptDTO);
	}
	
	/*
	 * Method to manage drop Foreign Key Constraint Script xml script request
	 */
	@PostMapping("/dropForeignKeyConstraint/")
	@ResponseStatus(HttpStatus.OK)
	public String generateDropForeignKeyConstraintScriptRequest(@RequestBody DropForeignKeyConstraintScriptDTO dropForeignKeyConstraintScriptDTO) {
		return scriptGeneratorService.generateDropForeignKeyConstraintLiquibaseXMLScript(dropForeignKeyConstraintScriptDTO);
	}
	
	/*
	 * Method to manage delete query Script xml script request
	 */
	@PostMapping("/deleteData/")
	@ResponseStatus(HttpStatus.OK)
	public String generateDeleteScriptRequest(@RequestBody DeleteDataScriptDTO deleteDataScriptDTO) {
		return scriptGeneratorService.generateDeleteDataLiquibaseXMLScript(deleteDataScriptDTO);
	}
	
	/*
	 * Method to manage insert query Script xml script request
	 */
	@PostMapping("/insertData/")
	@ResponseStatus(HttpStatus.OK)
	public String generateInsertScriptRequest(@RequestBody InsertDataScriptDTO insertDataScriptDTO) {
		return scriptGeneratorService.generateInsertDataLiquibaseXMLScript(insertDataScriptDTO);
	}
	
	/*
	 * Method to manage update query Script xml script request
	 */
	@PostMapping("/updatetData/")
	@ResponseStatus(HttpStatus.OK)
	public String generateUpdateScriptRequest(@RequestBody UpdateDataScriptDTO updateDataScriptDTO) {
		return scriptGeneratorService.generateUpdateDataLiquibaseXMLScript(updateDataScriptDTO);
	}
}
