package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddColumnScriptDTO extends ScriptDTO {	
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("column_type")
	private String columnType;
	
	@JsonProperty("column_default")
	private String columnDefault;
	
	@JsonProperty("is_nullable")
	private Boolean nullable;
	
	@JsonProperty("is_unique")
	private Boolean unique;
	
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
	
	public String getColumnType() {
		return columnType;
	}
	
	public String getColumnDefault() {
		return columnDefault;
	}
	
	public Boolean getIsNullable() {
		return nullable;
	}
	
	public Boolean getUnique() {
		return unique;
	}
	
	/*
	 * Setter methods
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	
	public void setIsNullable(Boolean nullable) {
		this.nullable = nullable;
	}
	
	public void setUnique(Boolean unique) {
		this.unique = unique;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ schemaName.hashCode() ^ columnName.hashCode() ^ columnType.hashCode() ^ unique.hashCode() ^ nullable.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof AddColumnScriptDTO))	return false;
		
		AddColumnScriptDTO script = (AddColumnScriptDTO)o;
		
		return super.equals(script) && script.tableName.equals(this.tableName) && script.schemaName.equals(this.schemaName) && script.columnName.equals(columnName) &&
				script.columnType.equals(this.columnType) && script.nullable.equals(this.nullable) && script.unique.equals(this.unique);
	} 
}
