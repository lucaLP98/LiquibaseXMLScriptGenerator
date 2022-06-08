package it.alten.tirocinio.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
/*
 * Data Transfer Object for Database Schema information
 */

public class SchemaDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	public SchemaDTO() {
		
	}
	
	public SchemaDTO(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public String getSchemaName() {
		return schemaName;
	}
	
	@Override
	public String toString() {
		return schemaName;
	}
}
