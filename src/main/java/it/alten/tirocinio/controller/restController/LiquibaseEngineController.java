package it.alten.tirocinio.controller.restController;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.services.LiquibaseEngineService;

/*
 * Spring Controller for Liquibase operation
 */
@RestController
@RequestMapping("/liquibaseEngine/")
public class LiquibaseEngineController {
	private final LiquibaseEngineService liquibaseEngineService;
	
	/*
	 * Contructor
	 */
	public LiquibaseEngineController(LiquibaseEngineService liquibaseEngineService) {
		this.liquibaseEngineService = liquibaseEngineService;
	}
	
	/*
	 * GET Requests
	 */
	@GetMapping({"runChangeLog", "runChangeLog/"})
	@ResponseStatus(HttpStatus.OK)
	public String runCHangeLog() {
		return (liquibaseEngineService.runChangeLogScript()) ? "success" : "error";
	}
}
