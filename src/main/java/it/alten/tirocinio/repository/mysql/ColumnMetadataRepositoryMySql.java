package it.alten.tirocinio.repository.mysql;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.ColumnMetadata;
import it.alten.tirocinio.repository.ColumnMetadataRepository;

/*
 * CRUD operation for the table information_schema.columns which contains all information about metadata of Columns of Database Tables for MySql DBMS
 */
@Repository
@Profile("mysql")
public interface ColumnMetadataRepositoryMySql extends ColumnMetadataRepository, CrudRepository<ColumnMetadata, String> {
	/*
	 * Query for retrieve metadata of all columns present in the database
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default FROM information_schema.columns", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumns();
	
	/*
	 * Query for retrieve metadata of all columns present in the database by their membership table (required also the table's schema)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default  FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name)", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of specific column by its name and membership table (required also the table's schema)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default  FROM information_schema.columns where (table_schema = :table_schema AND table_name = :table_name AND column_name = :column_name)", nativeQuery = true)
	@Override
	ColumnMetadata getDBColumnByNameAndTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName, @Param("column_name") String columnName);
	

	/*
	 * Query for retrieve metadata of column with not null constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default  FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name AND is_nullable = \"NO\" AND column_key <> \"PRI\")", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBNotNullColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of nullable column (without not null) constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default  FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name AND is_nullable = \"YES\")", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBNullableColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of integer column by their membership table and schema 
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default  FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name AND ( column_type=\"int\" OR  column_type=\"mediumint\" OR column_type=\"tinyint\" OR column_type=\"bigint\"))", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBIntegerColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata column with default value not null by their membership table and schema 
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default  FROM information_schema.columns WHERE (table_schema = :table_schema AND table_name = :table_name AND column_default is not null AND column_default <> \"\")", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumnsWithDefaultValueByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);

	/*
	 * Query for retrieve metadata of all columns present in the database by their data type and membership table (required also the table's schema)
	 */
	@Query(value = "SELECT table_schema, table_name, column_name, column_type, is_nullable, column_key, column_default  FROM information_schema.columns WHERE (column_type = :column_type AND table_schema = :table_schema AND table_name = :table_name)", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumnsByDataTypeAndTable(@Param("column_type") String columnType, @Param("table_schema") String tableSchema, @Param("table_name") String tableName);
}
