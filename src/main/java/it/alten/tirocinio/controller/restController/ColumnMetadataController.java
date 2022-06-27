package it.alten.tirocinio.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataListDTO;
import it.alten.tirocinio.services.ColumnMetadataService;

/*
 * Spring Controller for operation on Database Columns
 */
@RestController
@RequestMapping("/api/columns/")
public class ColumnMetadataController {
	private final ColumnMetadataService columnMetadataService;
	
	/*
	 * Constructors
	 */
	public ColumnMetadataController(ColumnMetadataService columnMetadataService) {
		this.columnMetadataService = columnMetadataService;
	}
	
	/*
	 * GET requests
	 */
	@GetMapping("/all/")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataListDTO gettAllColumns() {
		return columnMetadataService.getAllColumns();
	}
	
	@GetMapping("/byTable/{schemaName}&{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataListDTO gettAllColumnsByTable(@PathVariable String schemaName, @PathVariable String tableName) {
		return columnMetadataService.getAllColumnsByTable(schemaName, tableName);
	}
	
	@GetMapping("/byName/{schemaName}&{tableName}&{columnName}")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataDTO getColumnsByNameAndTable(@PathVariable String schemaName, @PathVariable String tableName, @PathVariable String columnName) {
		return columnMetadataService.getColumnByNameAndTable(schemaName, tableName, columnName);
	}
	
	@GetMapping("/NotNull/{schemaName}&{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataListDTO getColumnsNotNullByTable(@PathVariable String schemaName, @PathVariable String tableName) {
		return columnMetadataService.getColumnNotNullByTable(schemaName, tableName);
	}
	
	@GetMapping("/Null/{schemaName}&{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataListDTO getColumnsNullableByTable(@PathVariable String schemaName, @PathVariable String tableName) {
		return columnMetadataService.getColumnNullableByTable(schemaName, tableName);
	}
	
	@GetMapping("/Integer/{schemaName}&{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataListDTO getIntegerColumnsByTable(@PathVariable String schemaName, @PathVariable String tableName) {
		return columnMetadataService.getIntegerColumnByTable(schemaName, tableName);
	}
	
	@GetMapping("/WithDefaultValue/{schemaName}&{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataListDTO getColumnsWithDefaultByTable(@PathVariable String schemaName, @PathVariable String tableName) {
		return columnMetadataService.getColumnWithDefaultValueByTable(schemaName, tableName);
	}
	
	@GetMapping("/byDataType/{dataType}&{schemaName}&{tableName}")
	@ResponseStatus(HttpStatus.OK)
	public ColumnMetadataListDTO getColumnsByDataTypeAndTable(@PathVariable String schemaName, @PathVariable String tableName, @PathVariable String dataType) {
		return columnMetadataService.getColumnByTypeAndTable(dataType, schemaName, tableName);
	}
}
