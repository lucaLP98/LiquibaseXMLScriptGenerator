package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CheckConstraintMetadata{
	@Id
	@Column(name = "constraint_name", insertable=false, updatable=false)
	private String constraintName;
	
	@Column(name = "table_schema", insertable=false, updatable=false)
	private String tableSchema;
	
	@Column(name = "table_name", insertable=false, updatable=false)
	private String tableName;
	
	@Column(name = "constraint_type", insertable=false, updatable=false)
	private String constraintType;
	
	@Column(name = "check_clause", insertable=false, updatable=false)
	private String checkClause;
	
	/*
	 * Getter methods
	 */
	public String getConstraintName() {
		return constraintName;
	}
	
	public String getTableSchema() {
		return tableName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getConstraintType() {
		return constraintType;
	}
	
	public String getCheckClause() {
		return checkClause;
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
	
	public void setCheckClause(String checkClause) {
		this.checkClause = checkClause;
	}
	
	/*
	 * Override methods
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		if(!(o instanceof CheckConstraintMetadata))	return false;
		
		CheckConstraintMetadata c = (CheckConstraintMetadata)o;
		return this.constraintName.equals(c.constraintName) && this.tableName.equals(c.tableName) && this.tableSchema.equals(c.tableSchema) && this.checkClause.equals(c.checkClause);
	}
	
	@Override
	public int hashCode() {
		return tableName.hashCode() ^ tableSchema.hashCode() ^ constraintName.hashCode() ^ checkClause.hashCode();
	}
}
