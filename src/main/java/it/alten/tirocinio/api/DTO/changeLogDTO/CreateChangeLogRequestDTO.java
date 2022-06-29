package it.alten.tirocinio.api.DTO.changeLogDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateChangeLogRequestDTO {
	@JsonProperty("changelog_id")
	private String changeLogId;
	
	/*
	 * Setter methods
	 */
	public void setChangeLogId(String changeLogId) {
		this.changeLogId = changeLogId;
	}
	
	/*
	 * Getter methods
	 */
	public String getChangeLogId() {
		return changeLogId;
	}
}
