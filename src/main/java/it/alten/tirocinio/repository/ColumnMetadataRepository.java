package it.alten.tirocinio.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.alten.tirocinio.model.ColumnMetadata;

/*
 * CRUD operation for the table information_schema.columns which contains all information about metadata of Columns of Database Tables 
 */
public interface ColumnMetadataRepository extends CrudRepository<ColumnMetadata, String> {
	/*
	 * Query for retrieve metadata of all columns present in the database
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key FROM information_schema.columns", nativeQuery = true)
	Set<ColumnMetadata> getAllDBColumns();
	
	/*
	 * Query for retrieve metadata of all columns present in the database by their membership table (required also the table's schema)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name)", nativeQuery = true)
	Set<ColumnMetadata> getAllDBColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of specific column by its name and membership table (required also the table's schema)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key FROM information_schema.columns where (table_schema = :table_schema AND table_name = :table_name AND column_name = :column_name)", nativeQuery = true)
	ColumnMetadata getDBColumnByNameAndTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName, @Param("column_name") String columnName);
	

	/*
	 * Query for retrieve metadata of column with not null constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name AND is_nullable = \"NO\" AND column_key <> \"PRI\")", nativeQuery = true)
	Set<ColumnMetadata> getAllDBNotNullColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of nullable column (without not null) constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name AND is_nullable = \"YES\")", nativeQuery = true)
	Set<ColumnMetadata> getAllDBNullableColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of integer column by their membership table and schema 
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name AND ( column_type=\"int\" OR  column_type=\"mediumint\" OR column_type=\"tinyint\" OR column_type=\"bigint\"))", nativeQuery = true)
	Set<ColumnMetadata> getAllDBIntegerColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
}
