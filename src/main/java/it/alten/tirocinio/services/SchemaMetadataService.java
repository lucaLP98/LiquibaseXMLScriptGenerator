package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.entityDTO.SchemaListDTO;

/*
 * Service for Database SchemaMetadatMySql Information
 */
public interface SchemaMetadataService {
	/*
	 * Get all schema which are present in DB 
	 */
	SchemaListDTO getAllDatabaseSchema();
}
