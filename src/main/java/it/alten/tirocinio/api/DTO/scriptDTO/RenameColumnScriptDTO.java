package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RenameColumnScriptDTO extends ScriptDTO{
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String table_name;
	
	@JsonProperty("new_column_name")
	private String newColumnName;
	
	@JsonProperty("old_column_name")
	private String oldColumnName;
	
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
	
	public String getNewColumnName() {
		return newColumnName;
	}
	
	public String getOldColumnName() {
		return oldColumnName;
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
	
	public void setNewColumnName(String newColumnName) {
		this.newColumnName = newColumnName;
	}
	
	public void setOldColumnName(String oldColumnName) {
		this.oldColumnName = oldColumnName;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
}
