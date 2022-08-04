package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColumnMetadata {
	@Column(name = "table_name", insertable=false, updatable=false)
	private String tableName;
	
	@Column(name = "table_schema", insertable=false, updatable=false)
	private String tableSchema;
	
	@Id
	@Column(name = "column_name", insertable=false, updatable=false)
	private String columnName;
	
	@Column(name = "column_type", insertable=false, updatable=false)
	private String columnType;
	
	@Column(name = "is_nullable", insertable=false, updatable=false)
	private String isNullable;
	
	@Column(name = "column_default", insertable=false, updatable=false)
	private String columnDefault;
	
	@Column(name = "column_key", insertable=false, updatable=false)
	private String columnKey;

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
	
	/*
	 * Override methods
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		if(!(o instanceof ColumnMetadata))	return false;
		
		ColumnMetadata c = (ColumnMetadata)o;
		return this.columnName.equals(c.columnName) && this.tableName.equals(c.tableName) && this.tableSchema.equals(c.tableSchema);
	}
	
	@Override
	public int hashCode() {
		return columnName.hashCode() ^ tableName.hashCode() ^ tableSchema.hashCode();
	}
}
