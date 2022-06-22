package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "information_schema.key_column_usage")
public class KeyColumnMetadata {
	@Id
	@Column(name = "constraint_name", insertable=false, updatable=false)
	private String constraintName;
	
	@Column(name = "table_schema", insertable=false, updatable=false)
	private String baseTableSchema;
	
	@Column(name = "table_name", insertable=false, updatable=false)
	private String baseTableName;
	
	@Column(name = "column_name", insertable=false, updatable=false)
	private String baseColumnName;
	
	@Column(name = "referenced_table_schema", insertable=false, updatable=false)
	private String referencedTableSchema;
	
	@Column(name = "referenced_table_name", insertable=false, updatable=false)
	private String referencedTableName;
	
	@Column(name = "referenced_column_name", insertable=false, updatable=false)
	private String referencedColumnName;
	
	@Column(name = "delete_rule", insertable=false, updatable=false)
	private String onDeleteClause;
	
	@Column(name = "update_rule", insertable=false, updatable=false)
	private String onUpdateClause;
	
	/*
	 * Getter methods
	 */
	public String getConstraintName() {
		return constraintName;
	}
	
	public String getBaseTableSchema() {
		return baseTableSchema;
	}
	
	public String getBaseTableName() {
		return baseTableName;
	}
	
	public String getBaseColumnName() {
		return baseColumnName;
	}
	
	public String getReferencedTableSchema() {
		return referencedTableSchema;
	}
	
	public String getReferencedTableName() {
		return referencedTableName;
	}
	
	public String getReferencedColumnName() {
		return referencedColumnName;
	}
	
	public String getOnUpdateClause() {
		return onUpdateClause;
	}
	
	public String getOnDeleteClause() {
		return onDeleteClause;
	}
	
	/*
	 * Setter methods
	 */
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	
	public void setBaseTableSchema(String baseTableSchema) {
		this.baseTableSchema = baseTableSchema;
	}
	
	public void setBaseTableName(String baseTableName) {
		this.baseTableName = baseTableName;
	}
	
	public void setBaseColumnName(String baseColumnName) {
		this.baseColumnName = baseColumnName;
	}
	
	public void setReferencedTableSchema(String referencedTableSchema) {
		this.referencedTableSchema = referencedTableSchema;
	}
	
	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}
	
	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}
	
	public void setOnUpdateClause(String onUpdateClause) {
		this.onUpdateClause = onUpdateClause;
	}
	
	public void setOnDeleteClause(String onDeleteClause) {
		this.onDeleteClause = onDeleteClause;
	}
}
