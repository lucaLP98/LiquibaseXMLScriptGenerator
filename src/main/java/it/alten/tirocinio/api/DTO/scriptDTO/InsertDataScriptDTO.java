package it.alten.tirocinio.api.DTO.scriptDTO;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for the information about a "Insert query"
 */
public class InsertDataScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
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
	
	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ schemaName.hashCode() ^ columns.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof InsertDataScriptDTO))	return false;
		
		InsertDataScriptDTO script = (InsertDataScriptDTO)o;
		
		boolean equal =  super.equals(script) && script.tableName.equals(this.tableName) && script.schemaName.equals(this.schemaName);
	
		if(!(this.columns.keySet().containsAll(script.columns.keySet())))	equal = false;
		if(!(this.columns.entrySet().containsAll(script.columns.entrySet())))	equal = false;
		
		return equal;
	}
}
