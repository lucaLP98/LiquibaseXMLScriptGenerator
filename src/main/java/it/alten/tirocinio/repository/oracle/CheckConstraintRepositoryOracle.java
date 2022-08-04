package it.alten.tirocinio.repository.oracle;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.CheckConstraintMetadata;
import it.alten.tirocinio.repository.CheckConstraintRepository;

/*
 * CRUD operation for the table information_schema.CHECK_CONSTRAINTS which contains all information about metadata of check constraint in Oracle DBMS
 */
@Repository
@Profile("oracle")
public interface CheckConstraintRepositoryOracle extends CheckConstraintRepository, CrudRepository<CheckConstraintMetadata, String> {
	/*
	 * Query for retrieve metadata of all CHECK Constraints of the input table (schema required)
	 */
	@Query(value = "SELECT OWNER as table_schema, CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME, SEARCH_CONDITION as check_clause FROM SYS.ALL_CONSTRAINTS WHERE CONSTRAINT_TYPE = 'C' AND TABLE_NAME = :table_name AND OWNER = :table_schema", nativeQuery = true)
	@Override
	Set<CheckConstraintMetadata> getCheckConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
}
