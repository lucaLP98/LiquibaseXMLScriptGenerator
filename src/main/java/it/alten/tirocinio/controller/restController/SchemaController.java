package it.alten.tirocinio.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.services.SchemaService;
import it.alten.tirocinio.api.DTO.SchemaListDTO;

/*
 * Spring Controller for operation on Database Schemas
 */
@RestController
@RequestMapping("/api/schema/")
public class SchemaController {
	private final SchemaService schemaService;
	
	public SchemaController(SchemaService schemaService) {
		this.schemaService = schemaService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public SchemaListDTO getAllDatabaseSchema(){
		return schemaService.getAllDatabaseSchema();
	}
}
