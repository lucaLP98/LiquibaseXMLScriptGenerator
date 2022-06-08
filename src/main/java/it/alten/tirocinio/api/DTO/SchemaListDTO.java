package it.alten.tirocinio.api.DTO;

import java.util.List;
/*
 * List of Data Transfer Object for Database Schema information
 */
public class SchemaListDTO {
	private List<SchemaDTO> schemaList;
	
	public SchemaListDTO() {
		
	}
	
	public SchemaListDTO(List<SchemaDTO> schemaList) {
		this.schemaList = schemaList;
	}
	
	public void setSchemaList(List<SchemaDTO> schemaList) {
		this.schemaList = schemaList;
	}
	
	public List<SchemaDTO> getSchemaList() {
		return this.schemaList;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for(SchemaDTO s : schemaList) {
			ret = s + " ";
		}
		
		return ret;
	}
}
