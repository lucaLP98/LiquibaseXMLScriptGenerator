package it.alten.tirocinio.repository.oracle;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.TableConstraintMetadata;
import it.alten.tirocinio.repository.TableConstraintMetadataRepository;

/*
 * CRUD operation for the table information_schema.TABLE_CONSTRAINTS which contains all information about metadata of Database Table's Constraints for Oracle DBMS
 */
@Repository
@Profile("oracle")
public interface TableConstraintMetadataRepositoryOracle extends TableConstraintMetadataRepository, CrudRepository<TableConstraintMetadata, String> {
	/*
	 * Query for retrieve metadata of all UNIQUE Constraints of the input table (schema required)
	 */
	@Query(value = "SELECT owner as table_schema, table_name, constraint_name, constraint_type FROM sys.all_constraints WHERE (table_name = :table_name AND  owner = :table_schema AND constraint_type = 'U')", nativeQuery = true)
	@Override
	Set<TableConstraintMetadata> getUniqueConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
	
	/*
	 * Query for retrieve metadata of all FOREIGN KEY Constraints present in the database
	 */
	@Query(value = "SELECT owner as table_schema, table_name, constraint_name, constraint_type FROM sys.all_constraints WHERE (table_name = :table_name AND  owner = :table_schema AND constraint_type = 'R')", nativeQuery = true)
	@Override
	Set<TableConstraintMetadata> getForeignKeyConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
	
	/*
	 * Query for retrieve metadata of all Constraints present in the database
	 */
	@Query(value = "SELECT owner as table_schema, table_name, constraint_name, constraint_type FROM sys.all_constraints WHERE (table_name = :table_name AND  owner = :table_schema)", nativeQuery = true)
	@Override
	Set<TableConstraintMetadata> getAllConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
}
