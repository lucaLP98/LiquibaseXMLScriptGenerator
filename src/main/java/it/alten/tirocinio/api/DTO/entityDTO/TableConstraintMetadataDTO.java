package it.alten.tirocinio.api.DTO.entityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for Database Table's constraints informations
 */
public class TableConstraintMetadataDTO {
	@JsonProperty( "constraint_name")
	private String constraintName;
	
	@JsonProperty("table_schema")
	private String tableSchema;
	
	@JsonProperty("table_name")
	private String tableName;
	
	@JsonProperty("constraint_type")
	private String constraintType;
	
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
}
