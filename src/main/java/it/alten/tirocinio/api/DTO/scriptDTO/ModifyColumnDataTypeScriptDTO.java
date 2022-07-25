package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyColumnDataTypeScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String tableName;
	
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
		return tableName;
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
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ schemaName.hashCode() ^ columnName.hashCode() ^ oldColumnType.hashCode() ^ newColumnType.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof ModifyColumnDataTypeScriptDTO))	return false;
		
		ModifyColumnDataTypeScriptDTO script = (ModifyColumnDataTypeScriptDTO)o;
		
		return super.equals(script) && script.tableName.equals(this.tableName) && script.schemaName.equals(this.schemaName) && 
				script.columnName.equals(this.columnName) && script.oldColumnType.equals(this.oldColumnType) && script.newColumnType.equals(this.newColumnType);
	}
}
