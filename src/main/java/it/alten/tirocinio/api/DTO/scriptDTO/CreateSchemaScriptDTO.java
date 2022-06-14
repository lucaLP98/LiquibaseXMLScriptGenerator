package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateSchemaScriptDTO extends ScriptDTO {
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
