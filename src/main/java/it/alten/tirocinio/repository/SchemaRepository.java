package it.alten.tirocinio.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.alten.tirocinio.model.Schema;

/*
 * CRUD operation for the table information_schema.schemata which contains all information about metadata of Database Schemas 
 */
public interface SchemaRepository extends CrudRepository<Schema, String> {
	/*
	 * Query for retrieve of names of all schemas present in the database
	 */
	@Query(value = "select schema_name from information_schema.schemata schema_name", nativeQuery = true)
	Set<Schema> getAllDBSchema();
}
