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
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ schemaName.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof CreateSchemaScriptDTO))	return false;
		
		CreateSchemaScriptDTO script = (CreateSchemaScriptDTO)o;
		
		return super.equals(script) && script.schemaName.equals(this.schemaName);
	}
}
