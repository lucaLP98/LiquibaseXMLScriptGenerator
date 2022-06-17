package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataListDTO;

/*
 * Service for Database Constraints Information
 */
public interface TableConstraintMetadataService {
	/*
	 * get all DB constraints by table name (schema required)
	 */
	TableConstraintMetadataListDTO getAllConstraints(String tableName, String schemaName);
	
	/*
	 * get all DB unique constraints by table name (schema required)
	 */
	TableConstraintMetadataListDTO getAllUniqueConstraints(String tableName, String schemaName);
}
