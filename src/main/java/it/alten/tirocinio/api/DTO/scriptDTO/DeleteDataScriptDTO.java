package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for the information about a "Delete query"
 */
public class DeleteDataScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("where_condition")
	private String whereCondition;
	
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
}
