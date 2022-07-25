package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddAutoIncrementScriptDTO extends ScriptDTO {
	@JsonProperty("schema_name")
	private String schemaName;
	
	@JsonProperty("table_name")
	private String tableName;
	
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
		return tableName;
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
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ schemaName.hashCode() ^ columnName.hashCode() ^ startWith.hashCode() ^ incrementBy.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof AddAutoIncrementScriptDTO))	return false;
		
		AddAutoIncrementScriptDTO script = (AddAutoIncrementScriptDTO)o;
		
		return super.equals(script) && script.tableName.equals(this.tableName) && script.schemaName.equals(this.schemaName) && script.columnName.equals(columnName) &&
				script.startWith.equals(this.startWith) && script.incrementBy.equals(this.incrementBy);
	}
}
