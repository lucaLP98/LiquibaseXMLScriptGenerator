package it.alten.tirocinio.api.DTO.changeLogDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * List of Data Transfer Object for ChangeSet information
 */
public class ChangeSetListDTO {
	@JsonProperty("changeset_list")
	private List<ChangeSetDTO> changeSetList;
	
	/*
	 * Constructors
	 */
	public ChangeSetListDTO(List<ChangeSetDTO> changeSetList) {
		this.changeSetList = changeSetList;
	}
	
	/*
	 * Getter methods
	 */
	public List<ChangeSetDTO> getChangeSetList(){
		return changeSetList;
	}
	
	/*
	 * Setter methods
	 */
	public void setChangeSetList(List<ChangeSetDTO> changeSetList) {
		this.changeSetList = changeSetList;
	}
}
