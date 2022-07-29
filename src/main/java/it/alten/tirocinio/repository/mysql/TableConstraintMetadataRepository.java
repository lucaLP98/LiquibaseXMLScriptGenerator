package it.alten.tirocinio.repository.mysql;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.alten.tirocinio.model.mysql.TableConstraintMetadataMySql;

/*
 * CRUD operation for the table information_schema.TABLE_CONSTRAINTS which contains all information about metadata of Database Table's Constraints
 */
public interface TableConstraintMetadataRepository extends CrudRepository<TableConstraintMetadataMySql, String> {
	/*
	 * Query for retrieve metadata of all UNIQUE Constraints of the input table (schema required)
	 */
	@Query(value = "SELECT table_schema, table_name, constraint_name, constraint_type FROM information_schema.TABLE_CONSTRAINTS WHERE (table_name = :table_name AND  table_schema = :table_schema AND constraint_type = \"UNIQUE\")", nativeQuery = true)
	Set<TableConstraintMetadataMySql> getUniqueConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
	
	/*
	 * Query for retrieve metadata of all FOREIGN KEY Constraints present in the database
	 */
	@Query(value = "SELECT table_schema, table_name, constraint_name, constraint_type FROM information_schema.TABLE_CONSTRAINTS WHERE (table_name = :table_name AND  table_schema = :table_schema AND constraint_type = \"FOREIGN KEY\")", nativeQuery = true)
	Set<TableConstraintMetadataMySql> getForeignKeyConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
	
	/*
	 * Query for retrieve metadata of all Constraints present in the database
	 */
	@Query(value = "SELECT table_schema, table_name, constraint_name, constraint_type FROM information_schema.TABLE_CONSTRAINTS WHERE (table_name = :table_name AND  table_schema = :table_schema)", nativeQuery = true)
	Set<TableConstraintMetadataMySql> getAllConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
}
