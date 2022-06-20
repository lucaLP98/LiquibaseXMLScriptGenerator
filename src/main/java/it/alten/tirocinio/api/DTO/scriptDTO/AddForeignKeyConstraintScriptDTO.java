package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddForeignKeyConstraintScriptDTO extends ScriptDTO {
	@JsonProperty("constraint_name")
	private String constraintName;
	
	@JsonProperty("on_delete")
	private String onDelete;
	
	@JsonProperty("on_update")
	private String onUpdate;
	
	@JsonProperty("base_table_schema_name")
	private String baseSchemaName;
	
	@JsonProperty("base_table_name")
	private String baseTableName;
	
	@JsonProperty("base_column_name")
	private String baseColumnName;
	
	@JsonProperty("referenced_table_schema_name")
	private String referencedSchemaName;
	
	@JsonProperty("referenced_table_name")
	private String referencedTableName;
	
	@JsonProperty("referenced_column_name")
	private String referencedColumnName;
	
	/* 
	 * Getter methods
	 */
	public String getConstraintName() {
		return constraintName;
	}
	
	public String getOnDelete() {
		return onDelete;
	}
	
	public String getOnUpdate() {
		return onUpdate;
	}
	
	public String getBaseSchemaName() {
		return baseSchemaName;
	}
	
	public String getBaseTableName() {
		return baseTableName;
	}
	
	public String getBaseColumnName() {
		return baseColumnName;
	}
	
	public String getReferencedSchemaName() {
		return referencedSchemaName;
	}
	
	public String getReferencedTableName() {
		return referencedTableName;
	}
	
	public String getReferencedColumnName() {
		return referencedColumnName;
	}
	
	/*
	 * Setter methods
	 */
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	
	public void setOnDelete(String onDelete) {
		this.onDelete = onDelete;
	}
	
	public void setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
	}
	
	public void setBaseSchemaName(String baseSchemaName) {
		this.baseSchemaName = baseSchemaName;
	}
	
	public void setBaseTableName(String baseTableName) {
		this.baseTableName = baseTableName;
	}
	
	public void setBaseColumnName(String baseColumnName) {
		this.baseColumnName = baseColumnName;
	}
	
	public void setReferencedSchemaName(String referencedSchemaName) {
		this.referencedSchemaName = referencedSchemaName;
	}
	
	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}
	
	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}
}
