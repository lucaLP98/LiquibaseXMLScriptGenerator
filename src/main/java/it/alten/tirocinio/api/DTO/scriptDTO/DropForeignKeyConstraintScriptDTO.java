package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DropForeignKeyConstraintScriptDTO extends ScriptDTO {
	@JsonProperty("constraint_name")
	private String constraintName;
	
	@JsonProperty("base_table_schema_name")
	private String baseSchemaName;
	
	@JsonProperty("base_table_name")
	private String baseTableName;
	
	/* 
	 * Getter methods
	 */
	public String getConstraintName() {
		return constraintName;
	}
	
	public String getBaseSchemaName() {
		return baseSchemaName;
	}
	
	public String getBaseTableName() {
		return baseTableName;
	}
	
	/*
	 * Setter methods
	 */
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	
	public void setBaseSchemaName(String baseSchemaName) {
		this.baseSchemaName = baseSchemaName;
	}
	
	public void setBaseTableName(String baseTableName) {
		this.baseTableName = baseTableName;
	}
}
