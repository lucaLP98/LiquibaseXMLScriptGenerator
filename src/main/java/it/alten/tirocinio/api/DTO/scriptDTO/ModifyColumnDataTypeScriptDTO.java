package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyColumnDataTypeScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String table_name;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("old_column_type")
	private String oldColumnType;
	
	@JsonProperty("new_column_type")
	private String newColumnType;
	
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
	
	public String getNewColumnType() {
		return newColumnType;
	}
	
	public String getOldColumnType() {
		return oldColumnType;
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
	
	public void setNewColumnType(String newColumnType) {
		this.newColumnType = newColumnType;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public void setOldColumnType(String oldColumnType) {
		this.oldColumnType = oldColumnType;
	}
}
