package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataListDTO;

/*
 * Service for Database Columns Information
 */
public interface ColumnMetadataService {
	/*
	 * Get all columns which are present in database
	 */
	ColumnMetadataListDTO getAllColumns();
	
	/*
	 * Get all column which are present in DB by theirs membership table (schema required)
	 */
	ColumnMetadataListDTO getAllColumnsByTable(String schemaName, String tableName);
	
	/*
	 * Get only one column by its Name and membership table (schema required)
	 */
	ColumnMetadataDTO getColumnByNameAndTable(String schemaName, String tableName, String columnName);
	
	/*
	 * Get all columns with not null constraint by theirs membership table (schema required)
	 */
	ColumnMetadataListDTO getColumnNotNullByTable(String schemaName, String tableName);
	
	/*
	 * Get all columns without not null constraint by theirs membership table (schema required)
	 */
	ColumnMetadataListDTO getColumnNullableByTable(String schemaName, String tableName);
	
	/*
	 * Get all integer columns by theirs membership table (schema required)
	 */
	ColumnMetadataListDTO getIntegerColumnByTable(String schemaName, String tableName);
	
	/*
	 * Get all columns with default value not null by theirs membership table (schema required)
	 */
	ColumnMetadataListDTO getColumnWithDefaultValueByTable(String schemaName, String tableName);
	
	/*
	 * Get all columns by theirs data type and membership table (schema required)
	 */
	ColumnMetadataListDTO getColumnByTypeAndTable(String dataType, String schemaName, String tableName);
}
