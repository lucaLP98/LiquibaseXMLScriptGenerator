package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DropDefaultValueScriptDTO extends ScriptDTO{
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String table_name;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("default_value")
	private String defaultValue;
	
	@JsonProperty("column_type")
	private String columnType;
	
	/*
	 * Getter methods
	 */
	public String getSchemaName() {
		return schemaName;
	}
	
	public String getTableName() {
		return table_name;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public String getColumnType() {
		return columnType;
	}
	
	/*
	 * Setter methods
	 */
	public void setTableName(String table_name) {
		this.table_name = table_name;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
}
