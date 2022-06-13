package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for the information about a "Delete Column Script"
 */
public class DropColumnScriptDTO extends ScriptDTO {
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("column_name")
	private String columnName;
	
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
}
