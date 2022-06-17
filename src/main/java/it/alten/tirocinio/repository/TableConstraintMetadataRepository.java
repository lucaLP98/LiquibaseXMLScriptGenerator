package it.alten.tirocinio.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.alten.tirocinio.model.TableConstraintMetadata;

public interface TableConstraintMetadataRepository extends CrudRepository<TableConstraintMetadata, String> {
	@Query(value = "SELECT table_schema, table_name, constraint_name, constraint_type FROM information_schema.TABLE_CONSTRAINTS WHERE (table_name = :table_name AND  table_schema = :table_schema AND constraint_type = \"UNIQUE\")", nativeQuery = true)
	Set<TableConstraintMetadata> getUniqueConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
	
	@Query(value = "SELECT table_schema, table_name, constraint_name, constraint_type FROM information_schema.TABLE_CONSTRAINTS WHERE (table_name = :table_name AND  table_schema = :table_schema)", nativeQuery = true)
	Set<TableConstraintMetadata> getAllConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
}
