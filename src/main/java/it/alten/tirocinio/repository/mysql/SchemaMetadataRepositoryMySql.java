package it.alten.tirocinio.repository.mysql;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.SchemaMetadata;
import it.alten.tirocinio.repository.SchemaMetadataRepository;

/*
 * CRUD operation for the table information_schema.schemata which contains all information about metadata of Database Schemas in MySql DBMS
 */
@Repository
@Profile("mysql")
public interface SchemaMetadataRepositoryMySql extends SchemaMetadataRepository, CrudRepository<SchemaMetadata, String> {
	/*
	 * Query for retrieve of names of all schemas present in the database
	 */
	@Query(value = "select schema_name from information_schema.schemata schema_name", nativeQuery = true)
	@Override
	Set<SchemaMetadata> getAllDBSchema();
}
