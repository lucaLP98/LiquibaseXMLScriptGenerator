package it.alten.tirocinio.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.api.DTO.entityDTO.SchemaListDTO;
import it.alten.tirocinio.services.SchemaMetadataService;

/*
 * Spring Rest Controller for operation on Database Schemas
 */
@RestController
@RequestMapping("/api/schema/")
public class SchemaController {
	private final SchemaMetadataService schemaMetadataService;
	
	/*
	 * Constructors
	 */
	public SchemaController(SchemaMetadataService schemaMetadataService) {
		this.schemaMetadataService = schemaMetadataService;
	}
	
	/*
	 * GET requests
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public SchemaListDTO getAllDatabaseSchema(){
		return schemaMetadataService.getAllDatabaseSchema();
	}
}
