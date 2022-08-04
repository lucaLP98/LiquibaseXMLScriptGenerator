package it.alten.tirocinio.repository.oracle;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alten.tirocinio.model.KeyColumnMetadata;
import it.alten.tirocinio.repository.KeyColumnMetadataRepository;

@Repository
@Profile("oracle")
public interface KeyColumnMetadataRepositoryOracle extends KeyColumnMetadataRepository, CrudRepository<KeyColumnMetadata, String> {
	/*
	 * Query for retrieve metadata of all the columns involved in the constraint
	 */
	@Query(
		value = " select A.constraint_name as constraint_name, A.owner as table_schema, A.table_name as table_name, B.column_name as column_name,C.owner as referenced_table_schema, C.table_name as referenced_table_name, C.column_name as referenced_column_name, A.delete_rule as delete_rule, null as update_rule "
				+ "from (all_constraints A join all_cons_columns B on A.constraint_name = B.constraint_name) LEFT JOIN all_cons_columns C on A.r_constraint_name = C.constraint_name "
				+ "where A.owner = :constraint_schema AND A.constraint_name = :constraint_name",
		nativeQuery = true)
	KeyColumnMetadata getKeyColumnConstraintsByConstraintNameAndSchema(@Param("constraint_name") String constraintName, @Param("constraint_schema") String constraintSchema);
	
	/*
	 * Query for retrieve metadata of all the columns involved in the foreign key constraint
	 */
	@Query(
			value = "select A.constraint_name as constraint_name, A.owner as table_schema, A.table_name as table_name, B.column_name as column_name,C.owner as referenced_table_schema, C.table_name as referenced_table_name, C.column_name as referenced_column_name, A.delete_rule as delete_rule, null as update_rule "
					+ "from (all_constraints A join all_cons_columns B on A.constraint_name = B.constraint_name) join all_cons_columns C on A.r_constraint_name = C.constraint_name "
					+ "where A.constraint_type='R' AND A.owner = :constraint_schema AND A.constraint_name = :constraint_name", 
			nativeQuery = true)
	KeyColumnMetadata getKeyColumnForeignKeyConstraintsByConstraintNameAndSchema(@Param("constraint_name") String constraintName, @Param("constraint_schema") String constraintSchema);
	
	/*
	 * Query for retrieve metadata of all the columns involved in all foreign key constraint for input table
	 */
	@Query(
			value = "select A.constraint_name as constraint_name, A.owner as table_schema, A.table_name as table_name, B.column_name as column_name,C.owner as referenced_table_schema, C.table_name as referenced_table_name, C.column_name as referenced_column_name, A.delete_rule as delete_rule, null as update_rule "
					+ "from (all_constraints A join all_cons_columns B on A.constraint_name = B.constraint_name) join all_cons_columns C on A.r_constraint_name = C.constraint_name "
					+ "where A.constraint_type='R' AND A.owner = :constraint_schema AND A.table_name = :constraint_table", 
			nativeQuery = true)
	Set<KeyColumnMetadata> getKeyColumnForeignKeyConstraintsByTableAndSchema(@Param("constraint_table") String constraintTable, @Param("constraint_schema") String constraintSchema);
}
