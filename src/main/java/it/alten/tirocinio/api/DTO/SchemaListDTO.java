package it.alten.tirocinio.api.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * List of Data Transfer Object for Database Schema information
 */
public class SchemaListDTO {
	@JsonProperty("schema_list")
	private List<SchemaDTO> schemaList;
	
	/*
	 * Constructors
	 */
	public SchemaListDTO() { }
	
	public SchemaListDTO(List<SchemaDTO> schemaList) {
		this.schemaList = schemaList;
	}
	
	/*
	 * Setter methods
	 */
	public void setSchemaList(List<SchemaDTO> schemaList) {
		this.schemaList = schemaList;
	}
	
	/*
	 * Getter methods
	 */
	public List<SchemaDTO> getSchemaList() {
		return this.schemaList;
	}
}
