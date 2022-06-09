package it.alten.tirocinio.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for Database Schema information
 * 
 * Author : Luca Pastore
 * Target : Alten Italia SpA
 * File : SchemaDTO.java
 */
public class SchemaDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	/*
	 * Constructors
	 */
	public SchemaDTO() { }
	
	public SchemaDTO(String schemaName) {
		this.schemaName = schemaName;
	}
	
	/*
	 * Setter methods
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	/*
	 * Getter methods
	 */
	public String getSchemaName() {
		return schemaName;
	}
}
