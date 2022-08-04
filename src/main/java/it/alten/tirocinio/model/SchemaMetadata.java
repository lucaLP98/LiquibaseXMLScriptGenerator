package it.alten.tirocinio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/*
 * This entity class represents a Database schema
 */
@Entity
public class SchemaMetadata {
	@Id
	@Column(name = "schema_name", insertable=false, updatable=false)
	private String schemaName;
	
	/*
	 * Setter methods
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	/*
	 * Getter methods
	 */
	public String getSchemaName() {
		return schemaName;
	} 
	
	@Override
	public String toString() {
		return schemaName;
	}
	
	/*
	 * Override methods
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		if(!(o instanceof SchemaMetadata))	return false;
		
		SchemaMetadata c = (SchemaMetadata)o;
		return this.schemaName.equals(c.schemaName);
	}
	
	@Override
	public int hashCode() {
		return schemaName.hashCode();
	}
}
