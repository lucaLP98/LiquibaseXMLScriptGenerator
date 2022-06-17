package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DropUniqueConstraintScriptDTO extends ScriptDTO {
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("constraint_name")
	private String constrainName;
	
	/*
	 * Setter methods
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setConstrainName(String constrainName) {
		this.constrainName = constrainName;
	}
	
	/*
	 * Getter methods
	 */
	public String getTableSchema() {
		return tableSchema;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getConstrainName() {
		return constrainName;
	}	
}
