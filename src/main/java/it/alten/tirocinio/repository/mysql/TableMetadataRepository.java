package it.alten.tirocinio.repository.mysql;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.alten.tirocinio.model.mysql.TableMetadataMySql;

/*
 * CRUD operation for the table information_schema.tables which contains all information about metadata of Database Tables 
 */
public interface TableMetadataRepository extends CrudRepository<TableMetadataMySql, String> {
	/*
	 * Query for retrieve metadata of all tables present in the database
	 */
	@Query(value = "SELECT table_schema, table_name, table_type, table_rows, create_time, table_comment FROM INFORMATION_SCHEMA.TABLES", nativeQuery = true)
	Set<TableMetadataMySql> getAllDBTables();
	
	/*
	 * Query for retrieve metadata of all tables present in the database by their membership schema
	 */
	@Query(value = "SELECT table_schema, table_name, table_type, table_rows, create_time, table_comment FROM INFORMATION_SCHEMA.TABLES WHERE (table_schema = :table_schema)", nativeQuery = true)
	Set<TableMetadataMySql> getAllDBTablesBySchema(@Param("table_schema") String tableSchema);
	
	/*
	 * Query for retrieve metadata of table by its name and membership schema
	 */
	@Query(value = "SELECT table_schema, table_name, table_type, table_rows, create_time, table_comment FROM INFORMATION_SCHEMA.TABLES WHERE (table_schema = :table_schema AND table_name = :table_name)", nativeQuery = true)
	TableMetadataMySql getDBTablesByNameAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
}
