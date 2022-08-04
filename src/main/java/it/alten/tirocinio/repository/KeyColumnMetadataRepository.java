package it.alten.tirocinio.repository;

import java.util.Set;

import it.alten.tirocinio.model.KeyColumnMetadata;

public interface KeyColumnMetadataRepository {
	/*
	 * Query for retrieve metadata of all the columns involved in the constraint
	 */
	KeyColumnMetadata getKeyColumnConstraintsByConstraintNameAndSchema(String constraintName, String constraintSchema);
	
	/*
	 * Query for retrieve metadata of all the columns involved in the foreign key constraint
	 */
	KeyColumnMetadata getKeyColumnForeignKeyConstraintsByConstraintNameAndSchema(String constraintName, String constraintSchema);
	
	/*
	 * Query for retrieve metadata of all the columns involved in all foreign key constraint for input table
	 */
	Set<KeyColumnMetadata> getKeyColumnForeignKeyConstraintsByTableAndSchema(String constraintTable, String constraintSchema);
}
