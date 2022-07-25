package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DropDefaultValueScriptDTO extends ScriptDTO{
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String tableName;
	
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
		return tableName;
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
	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ schemaName.hashCode() ^ columnName.hashCode() ^ defaultValue.hashCode() ^ columnType.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof DropDefaultValueScriptDTO))	return false;
		
		DropDefaultValueScriptDTO script = (DropDefaultValueScriptDTO)o;
		
		return super.equals(script) && script.tableName.equals(this.tableName) && script.schemaName.equals(this.schemaName) && 
				script.columnName.equals(columnName) && script.defaultValue.equals(this.defaultValue) && script.columnType.equals(this.columnType);
	}
}
