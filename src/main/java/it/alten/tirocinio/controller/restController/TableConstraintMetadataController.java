package it.alten.tirocinio.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataListDTO;
import it.alten.tirocinio.services.TableConstraintMetadataService;

/*
 * Spring Controller for operation on Database Constraints
 */
@RestController
@RequestMapping("/api/constraint/")
public class TableConstraintMetadataController {
	private TableConstraintMetadataService tableConstraintMetadataService;
	
	/*
	 * Constructors
	 */
	public TableConstraintMetadataController(TableConstraintMetadataService tableConstraintMetadataService) {
		this.tableConstraintMetadataService = tableConstraintMetadataService;
	}
	
	/*
	 * GET requests
	 */
	@GetMapping("/unique/{schemaName}&{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public TableConstraintMetadataListDTO getAllUniqueConstraints(@PathVariable String schemaName, @PathVariable String tableName) {
		return tableConstraintMetadataService.getAllUniqueConstraints(tableName, schemaName);
	}
	
	@GetMapping("/foreignkey/{schemaName}&{tableNam}")
	@ResponseStatus(HttpStatus.OK)
	public TableConstraintMetadataListDTO getAllForeignKeyConstraints(@PathVariable String schemaName, @PathVariable String tableNam) {
		return tableConstraintMetadataService.getAllForeignKeyConstraints(tableNam, schemaName);
	}
	
	@GetMapping("/all/{schemaName}&{tableNam}")
	@ResponseStatus(HttpStatus.OK)
	public TableConstraintMetadataListDTO getAllConstraints(@PathVariable String schemaName, @PathVariable String tableNam) {
		return tableConstraintMetadataService.getAllConstraints(tableNam, schemaName);
	}
}
