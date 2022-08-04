package it.alten.tirocinio.repository.oracle;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.SchemaMetadata;
import it.alten.tirocinio.repository.SchemaMetadataRepository;

/*
 * CRUD operation for the table information_schema.schemata which contains all information about metadata of Database Schemas in Oracle DBMS
 */
@Repository
@Profile("oracle")
public interface SchemaMetadataRepositoryOracle  extends SchemaMetadataRepository, CrudRepository<SchemaMetadata, String> {
	/*
	 * Query for retrieve of names of all schemas present in the database in ORACLE DBMS
	 */
	@Query(value = "select username as schema_name from all_users", nativeQuery = true)
	@Override
	Set<SchemaMetadata> getAllDBSchema();
}
