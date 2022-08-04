package it.alten.tirocinio.repository;

import java.util.Set;

import it.alten.tirocinio.model.TableConstraintMetadata;

/*
 * CRUD operation for the table information_schema.TABLE_CONSTRAINTS which contains all information about metadata of Database Table's Constraints
 */
public interface TableConstraintMetadataRepository {
	/*
	 * Query for retrieve metadata of all UNIQUE Constraints of the input table (schema required)
	 */
	Set<TableConstraintMetadata> getUniqueConstraintsByTable(String tableName, String tableSchema);
	
	/*
	 * Query for retrieve metadata of all FOREIGN KEY Constraints present in the database
	 */
	Set<TableConstraintMetadata> getForeignKeyConstraintsByTable(String tableName,  String tableSchema);
	
	/*
	 * Query for retrieve metadata of all Constraints present in the database
	 */
	Set<TableConstraintMetadata> getAllConstraintsByTable(String tableName, String tableSchema);
}
