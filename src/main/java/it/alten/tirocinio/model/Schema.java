package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * This entity class represents a Database schema
 */
@Entity
@Table(name = "information_schema.schemata")
public class Schema {
	@Id
	@Column(name = "schema_name", insertable=false, updatable=false)
	private String schemaName;
	
	public void serSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public String getSchemaName() {
		return schemaName;
	} 
	
	@Override
	public String toString() {
		return schemaName;
	}
}
