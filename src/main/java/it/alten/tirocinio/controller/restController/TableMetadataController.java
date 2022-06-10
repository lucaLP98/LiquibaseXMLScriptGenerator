package it.alten.tirocinio.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataListDTO;
import it.alten.tirocinio.services.TableMetadataService;

/*
 * Spring Controller for operation on Database Tables
 */
@RestController
@RequestMapping("/api/tables/")
public class TableMetadataController {
	private final TableMetadataService tableMetadataService;
	
	/*
	 * Constructors
	 */
	public TableMetadataController(TableMetadataService tableMetadataService) {
		this.tableMetadataService = tableMetadataService;
	}
	
	/*
	 * GET requests
	 */
	@GetMapping("/all/")
	@ResponseStatus(HttpStatus.OK)
	public TableMetadataListDTO getAllTables() {
		return tableMetadataService.getAllTables();
	}
	
	@GetMapping("/bySchema/{schemaName}")
	@ResponseStatus(HttpStatus.OK)
	public TableMetadataListDTO getAllTablesBySchema(@PathVariable String schemaName) {		
		return tableMetadataService.getAllTablesBySchema(schemaName);
	}
	
	@GetMapping("/byName/{schemaName}.{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public TableMetadataDTO getAllTablesByNameAndSchema(@PathVariable String schemaName, @PathVariable String tableName) {
		return tableMetadataService.getTableByNameAndSchema(schemaName, tableName);
	}
}
