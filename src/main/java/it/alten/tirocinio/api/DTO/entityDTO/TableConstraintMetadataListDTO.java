package it.alten.tirocinio.api.DTO.entityDTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * List of Data Transfer Object for Database Table's constraints informations
 */
public class TableConstraintMetadataListDTO {
	@JsonProperty("constraint_list")
	private List<TableConstraintMetadataDTO> constraintsMetadata;
	
	/*
	 * Constructors
	 */
	public TableConstraintMetadataListDTO() { 
		constraintsMetadata = new ArrayList<>();
	}
	
	public TableConstraintMetadataListDTO(List<TableConstraintMetadataDTO> constraintsMetadata) {
		this.constraintsMetadata = constraintsMetadata;
	}
	
	/*
	 * Setter methods
	 */
	public void setConstraintsMetadata(List<TableConstraintMetadataDTO> constraintsMetadata) {
		this.constraintsMetadata.addAll(constraintsMetadata);
	}
	
	/*
	 * Getter methods
	 */
	public List<TableConstraintMetadataDTO> getConstraintsMetadata(){
		return constraintsMetadata;
	}
}
