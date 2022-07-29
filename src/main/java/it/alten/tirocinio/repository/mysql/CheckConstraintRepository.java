package it.alten.tirocinio.repository.mysql;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.alten.tirocinio.model.mysql.CheckConstraintMetadataMySql;

/*
 * CRUD operation for the table information_schema.CHECK_CONSTRAINTS which contains all information about metadata of check constraint 
 */
public interface CheckConstraintRepository extends CrudRepository<CheckConstraintMetadataMySql, String> {
	/*
	 * Query for retrieve metadata of all CHECK Constraints of the input table (schema required)
	 */
	@Query(value = "SELECT A.table_schema as table_schema, A.table_name as table_name, A.constraint_name as constraint_name, A.constraint_type as constraint_type, B.check_clause as check_clause FROM information_schema.TABLE_CONSTRAINTS as A join information_schema.CHECK_CONSTRAINTS as B on A.CONSTRAINT_NAME=B.CONSTRAINT_NAME WHERE (A.table_schema = :table_schema AND A.table_name = :table_name)", nativeQuery = true)
	Set<CheckConstraintMetadataMySql> getCheckConstraintsByTable(@Param("table_name") String tableName, @Param("table_schema") String tableSchema);
}
