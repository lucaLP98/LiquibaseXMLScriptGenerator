package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for the information about a "Delete Tale Script"
 */
public class DropTableScriptDTO extends ScriptDTO{	
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("cascade_constraint")
	private String cascadeConstraint;
	
	/*
	 * Getter methods
	 */
	public String getTableSchema() {
		return tableSchema;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getCascadeConstraint() {
		return cascadeConstraint;
	}
	
	/*
	 * Setter methods
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setCascadeConstraint(String cascadeConstraint) {
		this.cascadeConstraint = cascadeConstraint;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof DropTableScriptDTO))	return false;
		
		DropTableScriptDTO script = (DropTableScriptDTO)o;
		
		return super.equals(script) && script.tableName.equals(this.tableName) && script.tableSchema.equals(this.tableSchema) && script.cascadeConstraint.equals(this.cascadeConstraint);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ tableSchema.hashCode();
	}
}
