package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddAutoIncrementScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String table_name;
	
	@JsonProperty("new_column_name")
	private String newColumnName;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("start_with")
	private Integer startWith;
	
	@JsonProperty("increment_by")
	private Integer incrementBy;
	
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
	
	public String getColumnName() {
		return columnName;
	}
	
	public Integer getStratWith() {
		return startWith;
	}
	
	public Integer getIncrementBy() {
		return incrementBy;
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
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public void setIncrementBy(Integer incrementBy) {
		this.incrementBy = incrementBy;
	}
	
	public void setStartWith(Integer startWith) {
		this.startWith = startWith;
	}
}
