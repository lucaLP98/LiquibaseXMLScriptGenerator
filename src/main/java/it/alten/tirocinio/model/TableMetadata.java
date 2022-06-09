package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "information_schema.tables")
public class TableMetadata {
	@Id
	@Column(name = "table_name", insertable=false, updatable=false)
	private String tableName;
	
	@Column(name = "table_schema", insertable=false, updatable=false)
	private String tableSchema;
	
	@Column(name = "table_type", insertable=false, updatable=false)
	private String tableType;
	
	@Column(name = "table_rows", insertable=false, updatable=false)
	private Integer tableRows;
	
	@Column(name = "create_time", insertable=false, updatable=false)
	private String createTime;
	
	@Column(name = "table_comment", insertable=false, updatable=false)
	private String tableComment;
	
	/*
	 * Setter methods 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	
	public void setTableRows(Integer tableRows) {
		this.tableRows = tableRows;
	}
	
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	public String getTableType() {
		return tableType;
	}
	
	public Integer getTableRows() {
		return tableRows;
	}
	
	public String getTableComment() {
		return tableComment;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	@Override
	public String toString() {
		return tableSchema + "." + tableName;
	}
}
