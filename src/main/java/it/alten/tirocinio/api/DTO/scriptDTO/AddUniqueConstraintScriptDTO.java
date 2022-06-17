package it.alten.tirocinio.api.DTO.scriptDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUniqueConstraintScriptDTO extends ScriptDTO {
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("columns")
	private List<ColumnUnique> columns;
	
	@JsonProperty("constraint_name")
	private String constrainName;
	
	
	public static class ColumnUnique{
		@JsonProperty("column_name")
		private String columnName;
		
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		
		public String getColumnName() {
			return columnName;
		}
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
	
	public void setColumns(List<ColumnUnique> columns) {
		this.columns = columns;
	}
	public void setConstrainName(String constrainName) {
		this.constrainName = constrainName;
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
	
	public List<ColumnUnique> getColumns() {
		return columns;
	}
	
	public String getConstrainName() {
		return constrainName;
	}
}
