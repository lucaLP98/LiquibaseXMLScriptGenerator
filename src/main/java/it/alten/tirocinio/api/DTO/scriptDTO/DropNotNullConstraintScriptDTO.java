package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DropNotNullConstraintScriptDTO extends ScriptDTO {
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("column_type")
	private String columnDataType;
	
	/*
	 * Setter methods
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}
	
	/*
	 * Getter methods
	 */
	public String getTableSchema() {
		return tableSchema;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String getColumnDataType() {
		return columnDataType;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ tableSchema.hashCode() ^ columnName.hashCode() ^ columnDataType.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof DropNotNullConstraintScriptDTO))	return false;
		
		DropNotNullConstraintScriptDTO script = (DropNotNullConstraintScriptDTO)o;
		
		return super.equals(script) && script.tableName.equals(this.tableName) && script.tableSchema.equals(this.tableSchema) && 
				script.columnName.equals(this.columnName) && script.columnDataType.equals(this.columnDataType);
	}
}
