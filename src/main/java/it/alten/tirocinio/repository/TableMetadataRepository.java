package it.alten.tirocinio.repository;

import java.util.Set;

import it.alten.tirocinio.model.TableMetadata;

/*
 * CRUD operation for the table information_schema.tables which contains all information about metadata of Database Tables
 */ 
public interface TableMetadataRepository {
	/*
	 * Query for retrieve metadata of all tables present in the database
	 */
	Set<TableMetadata> getAllDBTables();
	
	/*
	 * Query for retrieve metadata of all tables present in the database by their membership schema
	 */
	Set<TableMetadata> getAllDBTablesBySchema(String tableSchema);
	
	/*
	 * Query for retrieve metadata of table by its name and membership schema
	 */
	TableMetadata getDBTablesByNameAndSchema(String tableSchema, String tableName);
}
