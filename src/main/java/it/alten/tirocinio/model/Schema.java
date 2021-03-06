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
		if(!(o instanceof Schema))	return false;
		
		Schema c = (Schema)o;
		return c.schemaName==this.schemaName;
	}
	
	@Override
	public int hashCode() {
		return schemaName.hashCode();
	}
}
