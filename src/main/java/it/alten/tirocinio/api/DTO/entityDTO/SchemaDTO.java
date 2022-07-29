package it.alten.tirocinio.api.DTO.entityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for Database SchemaMetadatMySql information
 */
public class SchemaDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
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
