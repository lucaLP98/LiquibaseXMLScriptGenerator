package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddColumnScriptDTO extends ScriptDTO {	
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("column_type")
	private String columnType;
	
	@JsonProperty("column_default")
	private String columnDefault;
	
	@JsonProperty("is_nullable")
	private String nullable;
	
	@JsonProperty("is_unique")
	private String unique;
	
	/*
	 * Getter methods
	 */
	public String getSchemaName() {
		return schemaName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String getColumnType() {
		return columnType;
	}
	
	public String getColumnDefault() {
		return columnDefault;
	}
	
	public String getIsNullable() {
		return nullable;
	}
	
	public String getUnique() {
		return unique;
	}
	
	/*
	 * Setter methods
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	
	public void setIsNullable(String nullable) {
		this.nullable = nullable;
	}
	
	public void setUnique(String unique) {
		this.unique = unique;
	}
}
