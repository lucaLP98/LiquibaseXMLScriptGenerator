package it.alten.tirocinio.api.DTO.scriptDTO;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDataScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("where_condition")
	private String whereCondition;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("columns")
	private Map<String, String> columns;
	
	/*
	 * Getter methods
	 */
	public String getSchemaName() {
		return schemaName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getWhereCondition() {
		return whereCondition;
	}
	
	public Map<String, String> getColumns() {
		return columns;
	}
	
	/*
	 * Setter methods
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}
	
	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}
}
