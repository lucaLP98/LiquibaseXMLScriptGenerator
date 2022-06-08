package it.alten.tirocinio.services;

import it.alten.tirocinio.api.DTO.SchemaListDTO;

/*
 * Service for Database Schema Information
 */
public interface SchemaService {
	/*
	 * Get all schema which are present in DB 
	 */
	SchemaListDTO getAllDatabaseSchema();
}
