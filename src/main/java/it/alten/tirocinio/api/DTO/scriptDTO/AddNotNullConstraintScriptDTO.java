package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddNotNullConstraintScriptDTO extends ScriptDTO {
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("column_type")
	private String columnDataType;
	
	@JsonProperty("default_null_value")
	private String defaultNullValue;
	
	/*
	 * Setter methods
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}
	
	public void setDefaultNullValue(String defaultNullValue) {
		this.defaultNullValue = defaultNullValue;
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
	
	public String getColumnName() {
		return columnName;
	}
	
	public String getColumnDataType() {
		return columnDataType;
	}
	
	public String getDefaultNullValue() {
		return defaultNullValue;
	}
}
