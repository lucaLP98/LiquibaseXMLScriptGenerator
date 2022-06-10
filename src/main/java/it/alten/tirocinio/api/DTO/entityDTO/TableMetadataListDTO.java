package it.alten.tirocinio.api.DTO.entityDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * List of Data Transfer Object for Database Tables information
 */
public class TableMetadataListDTO {
	@JsonProperty("table_list")
	private List<TableMetadataDTO> tablesMetadataList;
	
	/*
	 * Constructors
	 */
	public TableMetadataListDTO() {}
	
	public TableMetadataListDTO(List<TableMetadataDTO> tablesMetadataList) { this.tablesMetadataList = tablesMetadataList; }
	
	/*
	 * Setter methods
	 */
	public void setTablesMetadataList(List<TableMetadataDTO> tablesMetadataList) {
		this.tablesMetadataList = tablesMetadataList;
	}
	
	/*
	 * Getter methods
	 */
	public List<TableMetadataDTO> getTablesMetadataList() {
		return this.tablesMetadataList;
	}
}
