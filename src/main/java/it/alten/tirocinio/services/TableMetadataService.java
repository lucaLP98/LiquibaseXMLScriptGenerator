package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.TableMetadataListDTO;
import it.alten.tirocinio.api.DTO.TableMetadataDTO;

/*
 * Service for Database Tables Information
 */
public interface TableMetadataService {
	/*
	 * Get all tables which are present in database
	 */
	TableMetadataListDTO getAllTables();
	
	/*
	 * Get all tables which are present in DB by its membership schema
	 */
	TableMetadataListDTO getAllTablesBySchema(String schemaName);
	
	/*
	 * Get only one table by its Name and membership schema
	 */
	TableMetadataDTO getTableByNameAndSchema(String schemaName, String tableName);
}
