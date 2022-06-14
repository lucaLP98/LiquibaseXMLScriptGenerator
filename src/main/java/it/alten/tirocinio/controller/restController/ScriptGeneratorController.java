package it.alten.tirocinio.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateTableScriptDTO;
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
}
