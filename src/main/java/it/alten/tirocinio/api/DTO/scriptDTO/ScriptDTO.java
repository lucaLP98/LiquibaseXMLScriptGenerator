package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ScriptDTO {
	@JsonProperty("changeLog")
	private Boolean changeLog;
	
	@JsonProperty("id_changeset")
	private String idChangeset;
	
	@JsonProperty("author")
	private String author;
	
	@JsonProperty("on_error")
	private String onError;
	
	@JsonProperty("on_fail")
	private String onFail;
	
	@JsonProperty("add_to_changelog")
	private Boolean addToChangelog;
	
	/*
	 * Getter methods
	 */
	public Boolean getChangeLog() {
		return changeLog;
	}
	
	public String getIdChangeset() {
		return idChangeset;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getOnError() {
		return onError;
	}
	
	public String getOnFail() {
		return onFail;
	}
	
	
	public Boolean getAddToChangelog() {
		return addToChangelog;
	}

	/*
	 * Setter methods
	 */
	public void setChangeLog(Boolean changeLog) {
		this.changeLog = changeLog;
	}
	
	public void setIdChangeset(String idChangeset) {
		this.idChangeset = idChangeset;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setOnError(String onError) {
		this.onError = onError;
	}
	
	public void setOnFail(String onFail) {
		this.onFail = onFail;
	}
	
	public void setAddToChangelog(Boolean addToChangelog) {
		this.addToChangelog = addToChangelog;
	}
}
