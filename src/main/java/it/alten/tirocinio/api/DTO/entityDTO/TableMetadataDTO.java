package it.alten.tirocinio.api.DTO.entityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for Database Tables information
 */
public class TableMetadataDTO {	
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("table_type")
	private String tableType;
	
	@JsonProperty("table_rows")
	private Integer tableRows;
	
	@JsonProperty("create_time")
	private String createTime;
	
	@JsonProperty("table_comment")
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
}
