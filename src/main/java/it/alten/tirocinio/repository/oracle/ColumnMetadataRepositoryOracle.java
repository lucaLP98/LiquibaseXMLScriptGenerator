package it.alten.tirocinio.repository.oracle;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.ColumnMetadata;
import it.alten.tirocinio.repository.ColumnMetadataRepository;

/*
 * CRUD operation for the table information_schema.columns which contains all information about metadata of Columns of Database Tables for Oracle DBMS
 */
@Repository
@Profile("oracle")
public interface ColumnMetadataRepositoryOracle extends ColumnMetadataRepository, CrudRepository<ColumnMetadata, String>{
	/*
	 * Query for retrieve metadata of all columns present in the database
	 */
	@Query(
		value = "select A.OWNER AS TABLE_SCHEMA, A.TABLE_NAME AS TABLE_NAME, A.COLUMN_NAME AS COLUMN_NAME, A.nullable as is_nullable, A.DATA_TYPE as column_type, A.data_default as column_default, B.CONSTRAINT_TYPE AS column_key "
				+ "from  all_tab_columns A left join (ALL_CONSTRAINTs B JOIN ALL_CONS_COLUMNS C ON (B.CONSTRAINT_NAME = C.CONSTRAINT_NAME  and B.constraint_type = 'P')) on C.column_name = A.column_name ", 
		nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumns();
	
	/*
	 * Query for retrieve metadata of all columns present in the database by their membership table (required also the table's schema)
	 */
	@Query(
			value = "select A.OWNER AS TABLE_SCHEMA, A.TABLE_NAME AS TABLE_NAME, A.COLUMN_NAME AS COLUMN_NAME, A.nullable as is_nullable, A.DATA_TYPE as column_type, A.data_default as column_default, B.CONSTRAINT_TYPE AS column_key "
					+ "from  all_tab_columns A left join (ALL_CONSTRAINTs B JOIN ALL_CONS_COLUMNS C ON (B.CONSTRAINT_NAME = C.CONSTRAINT_NAME  and B.constraint_type = 'P')) on C.column_name = A.column_name "
					+ "where A.owner = :table_schema AND A.table_name = :table_name", 
			nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of specific column by its name and membership table (required also the table's schema)
	 */
	@Query(
			value = "select A.OWNER AS TABLE_SCHEMA, A.TABLE_NAME AS TABLE_NAME, A.COLUMN_NAME AS COLUMN_NAME, A.nullable as is_nullable, A.DATA_TYPE as column_type, A.data_default as column_default, B.CONSTRAINT_TYPE AS column_key "
					+ "from  all_tab_columns A left join (ALL_CONSTRAINTs B JOIN ALL_CONS_COLUMNS C ON (B.CONSTRAINT_NAME = C.CONSTRAINT_NAME  and B.constraint_type = 'P')) on C.column_name = A.column_name "
					+ "where A.owner = :table_schema AND A.table_name = :table_name AND A.column_name = :column_name", 
			nativeQuery = true)
	@Override
	ColumnMetadata getDBColumnByNameAndTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName, @Param("column_name") String columnName);
	

	/*
	 * Query for retrieve metadata of column with not null constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	@Query(value = "Select C.owner AS table_schema, C.table_name AS table_name, C.column_name as column_name, C.nullable as is_nullable, C.DATA_TYPE as column_type, null as column_default, null as column_key"
			+ "    from sys.all_tab_columns C"
			+ "    where C.owner= :table_schema AND C.table_name = :table_name AND C.nullable = 'N' AND C.column_name not in ("
			+ "        select  D.column_name"
			+ "        from (sys.ALL_constraints A join sys.all_cons_columns B on A.constraint_name=B.constraint_name) join sys.all_tab_columns D on B.column_name = D.column_name"
			+ "        where D.owner = :table_schema AND D.table_name = :table_name AND A.constraint_type='P')", 
			nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBNotNullColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of nullable column (without not null) constraint by their membership table and schema 
	 * (primary key columns are excluded)
	 */
	@Query(value = "Select owner AS table_schema, table_name, column_name, nullable as is_nullable, data_type as column_type, data_default as column_default, null as column_key  from sys.all_tab_columns where nullable = 'Y' AND owner = :table_schema AND table_name = :table_name", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBNullableColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata of integer column by their membership table and schema 
	 */
	@Query(value = "Select owner AS table_schema, table_name, column_name, nullable as is_nullable, data_type as column_type, data_default as column_default, null as column_key  from sys.all_tab_columns where data_type = 'NUMBER' AND owner = :table_schema AND table_name = :table_name", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBIntegerColumnsByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);
	
	/*
	 * Query for retrieve metadata column with default value not null by their membership table and schema 
	 */
	@Query(value = "Select owner AS table_schema, table_name, column_name, nullable as is_nullable, data_type as column_type, data_default as column_default, null as column_key  from sys.all_tab_columns where data_default is not null AND owner = :table_schema AND table_name = :table_name", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumnsWithDefaultValueByTableAndSchema(@Param("table_schema") String tableSchema, @Param("table_name") String tableName);

	/*
	 * Query for retrieve metadata of all columns present in the database by their data type and membership table (required also the table's schema)
	 */
	@Query(value = "Select owner AS table_schema, table_name, column_name, nullable as is_nullable, data_type as column_type, data_default as column_default, null as column_key  from sys.all_tab_columns where data_type = :column_type AND owner = :table_schema AND table_name = :table_name", nativeQuery = true)
	@Override
	Set<ColumnMetadata> getAllDBColumnsByDataTypeAndTable(@Param("column_type") String columnType, @Param("table_schema") String tableSchema, @Param("table_name") String tableName);
}
