package it.alten.tirocinio.api.DTO.changeLogDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Data Transfer Object for ChangeSet information
 */
public class ChangeSetDTO {
	@JsonProperty("changeset_id")
	private String changeSetId;
	
	/*
	 * Getter methods
	 */
	public String getChangeSetId() {
		return changeSetId;
	}
	
	/*
	 * Setter methods
	 */
	public void setChangeSetId(String changeSetId) {
		this.changeSetId = changeSetId;
	}
}
