package it.alten.tirocinio.api.DTO.entityDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * List of Data Transfer Object for Database Columns information
 */
public class ColumnMetadataListDTO {
	@JsonProperty("column_list")
	private List<ColumnMetadataDTO> columnsMetadataList;
	
	/*
	 * Constructors
	 */
	public ColumnMetadataListDTO() {}
	
	public ColumnMetadataListDTO(List<ColumnMetadataDTO> columnsMetadataList) {
		this.columnsMetadataList = columnsMetadataList;
	}
	
	/*
	 * Setter methods
	 */
	public void setColumnssMetadataList(List<ColumnMetadataDTO> columnsMetadataList) {
		this.columnsMetadataList = columnsMetadataList;
	}
	
	/*
	 * Getter methods
	 */
	public List<ColumnMetadataDTO> getColumnsMetadataList() {
		return this.columnsMetadataList;
	}
}
