package it.alten.tirocinio.api.DTO.scriptDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTableScriptDTO extends ScriptDTO{
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("pk_name")
	private String primaryKeyName;
	
	@JsonProperty("pk_type")
	private String primaryKeyType;
	
	@JsonProperty("columns")
	private List<AddColumnScriptDTO> columns;
	
	/*
	 * Setter methods
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
	
	public void setPrimaryKeyType(String primaryKeyType) {
		this.primaryKeyType = primaryKeyType;
	}
	
	public void setColumns(List<AddColumnScriptDTO> columns) {
		this.columns = columns;
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
	
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
	
	public String getPrimaryKeyType() {
		return primaryKeyType;
	}
	
	public List<AddColumnScriptDTO> getColumns(){
		return columns;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ tableName.hashCode() ^ tableSchema.hashCode() ^ primaryKeyName.hashCode() ^ primaryKeyType.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		
		if(!(o instanceof CreateTableScriptDTO))	return false;
		
		CreateTableScriptDTO script = (CreateTableScriptDTO)o;
		
		boolean equal = super.equals(script) && script.tableName.equals(this.tableName) && script.tableSchema.equals(this.tableSchema) && 
				script.primaryKeyName.equals(this.primaryKeyName) && script.primaryKeyType.equals(this.primaryKeyType);
		/*
		if(!(this.columns.containsAll(script.columns))) {
			equal = false;
		}*/
		
		return equal;
	}
}
