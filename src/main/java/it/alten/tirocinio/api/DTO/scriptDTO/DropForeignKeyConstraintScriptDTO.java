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
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ constraintName.hashCode() ^ baseSchemaName.hashCode() ^ baseTableName.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof DropForeignKeyConstraintScriptDTO))	return false;
		
		DropForeignKeyConstraintScriptDTO script = (DropForeignKeyConstraintScriptDTO)o;
		
		return super.equals(script) && script.constraintName.equals(this.constraintName) && script.baseSchemaName.equals(this.baseSchemaName) && script.baseTableName.equals(this.baseTableName);
	}
}
