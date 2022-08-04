package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TableConstraintMetadata {
	@Id
	@Column(name = "constraint_name", insertable=false, updatable=false)
	private String constraintName;
	
	@Column(name = "table_schema", insertable=false, updatable=false)
	private String tableSchema;
	
	@Column(name = "table_name", insertable=false, updatable=false)
	private String tableName;
	
	@Column(name = "constraint_type", insertable=false, updatable=false)
	private String constraintType;
	
	/*
	 * Getter methods
	 */
	public String getConstraintName() {
		return constraintName;
	}
	
	public String getTableSchema() {
		return tableSchema;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getConstraintType() {
		return constraintType;
	}
	
	/*
	 * Setter methods
	 */
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}
	
	/*
	 * Override methods
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		if(!(o instanceof TableConstraintMetadata))	return false;
		
		TableConstraintMetadata c = (TableConstraintMetadata)o;
		return this.constraintName.equals(c.constraintName) && this.tableName.equals(c.tableName) && this.tableSchema.equals(c.tableSchema);
	}
	
	@Override
	public int hashCode() {
		return tableName.hashCode() ^ tableSchema.hashCode() ^ constraintName.hashCode();
	}
}
