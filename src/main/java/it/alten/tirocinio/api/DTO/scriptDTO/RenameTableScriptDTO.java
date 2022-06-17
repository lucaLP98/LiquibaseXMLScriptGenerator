package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RenameTableScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("old_table_name")
	private String oldTableName;
	
	@JsonProperty("new_table_name")
	private String newTableName;
	
	/*
	 * Getter methods
	 */
	public String getSchemaName() {
		return schemaName;
	}
	
	public String getOldTableName() {
		return oldTableName;
	}
	
	public String getNewTableName() {
		return newTableName;
	}
	
	/*
	 * Setter methods
	 */
	public void setOldTableName(String oldTableName) {
		this.oldTableName = oldTableName;
	}
	
	public void setNewTableName(String newTableName) {
		this.newTableName = newTableName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
}
