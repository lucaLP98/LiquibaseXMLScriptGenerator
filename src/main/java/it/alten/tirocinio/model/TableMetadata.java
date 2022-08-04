package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TableMetadata {
	@Id
	@Column(name = "table_name", insertable=false, updatable=false)
	private String tableName;
	
	@Column(name = "table_schema", insertable=false, updatable=false)
	private String tableSchema;
	
	@Column(name = "table_rows", insertable=false, updatable=false)
	private Integer tableRows;
	
	/*
	 * Setter methods 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableRows(Integer tableRows) {
		this.tableRows = tableRows;
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
	
	public Integer getTableRows() {
		return tableRows;
	}
	
	@Override
	public String toString() {
		return tableSchema + "." + tableName;
	}
	
	/*
	 * Override methods
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		if(!(o instanceof TableMetadata))	return false;
		
		TableMetadata c = (TableMetadata)o;
		return this.tableName.equals(c.tableName) && this.tableSchema.equals(c.tableSchema);
	}
	
	@Override
	public int hashCode() {
		return tableName.hashCode() ^ tableSchema.hashCode();
	}
}
