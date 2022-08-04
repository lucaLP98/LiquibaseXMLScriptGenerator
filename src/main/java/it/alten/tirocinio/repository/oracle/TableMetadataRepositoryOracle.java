package it.alten.tirocinio.repository.oracle;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.TableMetadata;
import it.alten.tirocinio.repository.TableMetadataRepository;

/*
 * CRUD operation for the table information_schema.tables which contains all information about metadata of Database Tables in Oracle DBMS
 */ 
@Repository
@Profile("oracle")
public interface TableMetadataRepositoryOracle extends TableMetadataRepository, CrudRepository<TableMetadata, String> {
	/*
	 * Query for retrieve metadata of all tables present in the database
	 */
	@Query(value = "SELECT owner as table_schema, table_name, num_rows as table_rows FROM SYS.ALL_TABLES", nativeQuery = true)
	@Override
	Set<TableMetadata> getAllDBTables();
	
	/*
	 * Query for retrieve metadata of all tables present in the database by their membership schema
	 */
	@Query(value = "SELECT owner as table_schema, table_name, num_rows as table_rows FROM SYS.ALL_TABLES WHERE (owner = :table_schema)", nativeQuery = true)
	@Override
	Set<TableMetadata> getAllDBTablesBySchema(@Param("table_schema") String tableSchema);
	
	/*
	 * Query for retrieve metadata of table by its name and membership schema
	 */
	@Query(value = "SELECT owner as table_schema, table_name, num_rows as table_rows  FROM SYS.ALL_TABLES WHERE (owner = :table_schema AND table_name = :table_name)", nativeQuery = true)
	@Override
	TableMetadata getDBTablesByNameAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
}

