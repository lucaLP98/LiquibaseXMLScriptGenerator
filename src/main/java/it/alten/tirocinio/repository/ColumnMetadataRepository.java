package it.alten.tirocinio.repository;

import java.util.Set;

import it.alten.tirocinio.model.ColumnMetadata;

/*
 * CRUD operation for the table information_schema.columns which contains all information about metadata of Columns of Database Tables
 */
public interface ColumnMetadataRepository {
	/*
	 * Query for retrieve metadata of all columns present in the database
	 */
	Set<ColumnMetadata> getAllDBColumns();
	
	/*
	 * Query for retrieve metadata of all columns present in the database by their membership table (required also the table's schema)
	 */
	Set<ColumnMetadata> getAllDBColumnsByTableAndSchema(String tableSchema, String tableName);
	
	/*
	 * Query for retrieve metadata of specific column by its name and membership table (required also the table's schema)
	 */
	ColumnMetadata getDBColumnByNameAndTableAndSchema(String tableSchema, String tableName, String columnName);
	

	/*
	 * Query for retrieve metadata of column with not null constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	Set<ColumnMetadata> getAllDBNotNullColumnsByTableAndSchema(String tableSchema, String tableName);
	
	/*
	 * Query for retrieve metadata of nullable column (without not null) constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	Set<ColumnMetadata> getAllDBNullableColumnsByTableAndSchema(String tableSchema, String tableName);
	
	/*
	 * Query for retrieve metadata of integer column by their membership table and schema 
	 */
	Set<ColumnMetadata> getAllDBIntegerColumnsByTableAndSchema(String tableSchema, String tableName);
	
	/*
	 * Query for retrieve metadata column with default value not null by their membership table and schema 
	 */
	Set<ColumnMetadata> getAllDBColumnsWithDefaultValueByTableAndSchema(String tableSchema, String tableName);

	/*
	 * Query for retrieve metadata of all columns present in the database by their data type and membership table (required also the table's schema)
	 */
	Set<ColumnMetadata> getAllDBColumnsByDataTypeAndTable(String columnType, String tableSchema, String tableName);
}
