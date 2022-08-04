package it.alten.tirocinio.repository;

import java.util.Set;

import it.alten.tirocinio.model.SchemaMetadata;

/*
 * CRUD operation for the table information_schema.schemata which contains all information about metadata of Database Schemas
 */
public interface SchemaMetadataRepository {
	/*
	 * Query for retrieve of names of all schemas present in the database in ORACLE DBMS
	 */
	Set<SchemaMetadata> getAllDBSchema();
}
