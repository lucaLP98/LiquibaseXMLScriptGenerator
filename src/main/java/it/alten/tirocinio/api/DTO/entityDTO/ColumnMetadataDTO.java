package it.alten.tirocinio.api.DTO.entityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for Database Column information
 */
public class ColumnMetadataDTO {
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("column_name")
	private String columnName;
	
	@JsonProperty("is_nullable")
	private String isNullable;
	
	@JsonProperty("column_type")
	private String columnType;
	
	@JsonProperty("column_key")
	private String columnKey;
	
	@JsonProperty("column_default")
	private String columnDefault;
	
	/*
	 * Setter methods 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	
	/*
	 * Getter methods
	 */
	public String getTableName() {
		return tableName;
	}
	
	public String getTableSchema() {
		return tableSchema;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String getIsNullable() {
		return isNullable;
	}
	
	public String getColumnType() {
		return columnType;
	}
	
	public String getColumnKey() {
		return columnKey;
	}
	
	public String getColumnDefault() {
		return columnDefault;
	}
}
