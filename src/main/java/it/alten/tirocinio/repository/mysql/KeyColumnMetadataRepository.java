package it.alten.tirocinio.repository.mysql;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.alten.tirocinio.model.mysql.KeyColumnMetadataMySql;

public interface KeyColumnMetadataRepository extends CrudRepository<KeyColumnMetadataMySql, String> {
	/*
	 * Query for retrieve metadata of all the columns involved in the constraint
	 */
	@Query(value = "SELECT constraint_name, table_schema, table_name, column_name, referenced_table_schema, referenced_table_name, referenced_column_name FROM information_schema.KEY_COLUMN_USAGE WHERE (constraint_name = :constraint_name AND  table_schema = :constraint_schema)", nativeQuery = true)
	KeyColumnMetadataMySql getKeyColumnConstraintsByConstraintNameAndSchema(@Param("constraint_name") String constraintName, @Param("constraint_schema") String constraintSchema);
	
	/*
	 * Query for retrieve metadata of all the columns involved in the foreign key constraint
	 */
	@Query(value = "SELECT T.constraint_name as constraint_name, T.table_schema as table_schema, T.table_name as table_name, T.column_name as column_name, T.referenced_table_schema as referenced_table_schema, T.referenced_table_name as referenced_table_name, T.referenced_column_name as referenced_column_name, C.update_rule as update_rule, C.delete_rule as delete_rule FROM information_schema.KEY_COLUMN_USAGE as T JOIN information_schema.referential_constraints as C ON T.constraint_name = C.constraint_name WHERE (T.constraint_name = :constraint_name AND  T.table_schema = :constraint_schema)", nativeQuery = true)
	KeyColumnMetadataMySql getKeyColumnForeignKeyConstraintsByConstraintNameAndSchema(@Param("constraint_name") String constraintName, @Param("constraint_schema") String constraintSchema);
	
	/*
	 * Query for retrieve metadata of all the columns involved in all foreign key constraint for ine table
	 */
	@Query(value = "SELECT T.constraint_name as constraint_name, T.table_schema as table_schema, T.table_name as table_name, T.column_name as column_name, T.referenced_table_schema as referenced_table_schema, T.referenced_table_name as referenced_table_name, T.referenced_column_name as referenced_column_name, C.update_rule as update_rule, C.delete_rule as delete_rule FROM information_schema.KEY_COLUMN_USAGE as T JOIN information_schema.referential_constraints as C ON T.constraint_name = C.constraint_name WHERE (T.table_name = :constraint_table AND  T.table_schema = :constraint_schema)", nativeQuery = true)
	Set<KeyColumnMetadataMySql> getKeyColumnForeignKeyConstraintsByTableAndSchema(@Param("constraint_table") String constraintTable, @Param("constraint_schema") String constraintSchema);
}
