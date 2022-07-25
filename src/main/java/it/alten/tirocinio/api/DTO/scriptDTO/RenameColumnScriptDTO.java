package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RenameColumnScriptDTO extends ScriptDTO{
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String tableName;
	
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
		return tableName;
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
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ schemaName.hashCode() ^ newColumnName.hashCode() ^ oldColumnName.hashCode() ^ columnType.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof RenameColumnScriptDTO))	return false;
		
		RenameColumnScriptDTO script = (RenameColumnScriptDTO)o;
		
		return super.equals(script) && script.tableName.equals(this.tableName) && script.schemaName.equals(this.schemaName) && 
				script.newColumnName.equals(newColumnName) && script.oldColumnName.equals(this.oldColumnName) && script.columnType.equals(this.columnType);
	}
}
