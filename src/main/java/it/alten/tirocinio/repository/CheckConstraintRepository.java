package it.alten.tirocinio.repository;

import java.util.Set;

import it.alten.tirocinio.model.CheckConstraintMetadata;

/*
 * CRUD operation for the table information_schema.CHECK_CONSTRAINTS which contains all information about metadata of check constraint
 */
public interface CheckConstraintRepository {
	/*
	 * Query for retrieve metadata of all CHECK Constraints of the input table (schema required)
	 */
	Set<CheckConstraintMetadata> getCheckConstraintsByTable(String tableName, String tableSchema);
}
